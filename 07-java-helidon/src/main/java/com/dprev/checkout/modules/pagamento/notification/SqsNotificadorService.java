package com.dprev.checkout.modules.pagamento.notification;

import com.dprev.checkout.core.config.CheckoutConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Map;
import java.util.logging.Logger;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@ApplicationScoped
public class SqsNotificadorService implements Notificador {

    private static final Logger LOG = Logger.getLogger(SqsNotificadorService.class.getName());

    private final CheckoutConfig config;
    private final ObjectMapper mapper;

    @Inject
    public SqsNotificadorService(CheckoutConfig config) {
        this.config = config;
        this.mapper = new ObjectMapper(); // Instancia direta evita o conflito de múltiplos mappers do CDI/Jackson
    }

    private volatile SqsClient client;
    private volatile String queueUrl;

    @Override
    @Retry(maxRetries = 2, delay = 200)
    @Timeout(3000)
    @CircuitBreaker(requestVolumeThreshold = 4, failureRatio = 0.5, delay = 10000)
    @Fallback(fallbackMethod = "fallbackEnvio")
    public void enviarComprovante(String destino, BigDecimal valor) {
        if (!config.sqs().habilitado()) {
            LOG.fine("☁️ [SQS] Desabilitado por configuração.");
            return;
        }
        try {
            garantirCliente();
            String payload = mapper.writeValueAsString(Map.of("email", destino, "valor", valor));
            client.sendMessage(SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(payload)
                    .build());
            LOG.info("☁️ [AWS SQS LOCAL] Notificação enviada para a fila do LocalStack!");
        } catch (RuntimeException e) {
            throw e; 
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao montar/enviar mensagem SQS", e);
        }
    }

    void fallbackEnvio(String destino, BigDecimal valor) {
        LOG.warning(String.format("🚨 [SQS FALLBACK] Notificação para %s NÃO enviada (R$ %s). Candidata a reprocessamento posterior.", destino, valor));
    }

    private synchronized void garantirCliente() {
        if (client != null) {
            return;
        }
        CheckoutConfig.Sqs cfg = config.sqs();
        SqsClient c = SqsClient.builder()
                .endpointOverride(URI.create(cfg.endpoint()))
                .region(Region.of(cfg.region()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("mock_access_key", "mock_secret_key")))
                .httpClientBuilder(UrlConnectionHttpClient.builder())
                .build();
        queueUrl = c.createQueue(CreateQueueRequest.builder().queueName(cfg.fila()).build()).queueUrl();
        client = c;
    }

    @PreDestroy
    void fechar() {
        if (client != null) {
            client.close();
        }
    }
}