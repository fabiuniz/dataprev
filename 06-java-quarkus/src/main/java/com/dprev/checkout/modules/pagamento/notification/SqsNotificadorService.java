package com.dprev.checkout.modules.pagamento.notification;

import com.dprev.checkout.core.config.CheckoutConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.logging.Log;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Map;
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

/**
 * Escrita assíncrona-tolerante: se o SQS cair, o checkout NÃO falha (degradação graciosa).
 * Retry -> Timeout -> Circuit Breaker (Closed/Open/Half-Open) -> Fallback.
 */
@ApplicationScoped
public class SqsNotificadorService implements Notificador {

    @Inject CheckoutConfig config;
    @Inject ObjectMapper mapper;

    private volatile SqsClient client;
    private volatile String queueUrl;

    @Override
    @Retry(maxRetries = 2, delay = 200)
    @Timeout(3000)
    @CircuitBreaker(requestVolumeThreshold = 4, failureRatio = 0.5, delay = 10000)
    @Fallback(fallbackMethod = "fallbackEnvio")
    public void enviarComprovante(String destino, BigDecimal valor) {
        if (!config.sqs().habilitado()) {
            Log.debug("☁️ [SQS] Desabilitado por configuração.");
            return;
        }
        try {
            garantirCliente();
            String payload = mapper.writeValueAsString(Map.of("email", destino, "valor", valor));
            client.sendMessage(SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(payload)
                    .build());
            Log.info("☁️ [AWS SQS LOCAL] Notificação enviada para a fila do LocalStack!");
        } catch (RuntimeException e) {
            throw e; // deixa o Retry/CircuitBreaker agirem
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao montar/enviar mensagem SQS", e);
        }
    }

    // Mesma assinatura do método protegido
    void fallbackEnvio(String destino, BigDecimal valor) {
        Log.warnf("🚨 [SQS FALLBACK] Notificação para %s NÃO enviada (R$ %s). " +
                "Candidata a reprocessamento posterior (consistência eventual).", destino, valor);
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
        // createQueue é idempotente: retorna a URL se a fila já existir
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
