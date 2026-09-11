package com.tqi.checkout.notification;

public class SqsNotificadorService implements Notificador {

    private final com.amazonaws.services.sqs.AmazonSQS sqsClient;
    private final String queueUrl;

    public SqsNotificadorService() {
        // 1. Configuração do Endpoint do LocalStack
        com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration endpointConfig = 
            new com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration(
                "http://localhost:4566", 
                "us-east-1"
            );

        // 2. 🟢 SOLUÇÃO DO ERRO: Injeta chaves fictícias obrigatórias exigidas pelo SDK
        com.amazonaws.auth.AWSCredentialsProvider credentialsProvider = 
            new com.amazonaws.auth.AWSStaticCredentialsProvider(
                new com.amazonaws.auth.BasicAWSCredentials("mock_access_key", "mock_secret_key")
            );

        // 3. Constrói o cliente blindado com as credenciais locais
        this.sqsClient = com.amazonaws.services.sqs.AmazonSQSClientBuilder.standard()
                .withEndpointConfiguration(endpointConfig)
                .withCredentials(credentialsProvider) // 🟢 Vincula o provedor de chaves aqui
                .build();
                
        this.queueUrl = this.sqsClient.getQueueUrl("fila-notificacao-checkout").getQueueUrl();
    }

    @Override
    public void enviarComprovante(String destino, double valor) {
        String payload = String.format("{\"email\": \"%s\", \"valor\": %.2f}", destino, valor);
        
        try {
            com.amazonaws.services.sqs.model.SendMessageRequest request = 
                new com.amazonaws.services.sqs.model.SendMessageRequest(queueUrl, payload);
            sqsClient.sendMessage(request);
            System.out.println("☁️ [AWS SQS LOCAL] Notificação enviada para a fila do LocalStack!");
        } catch (Exception e) {
            System.err.println("🚨 [SQS ERRO] Falha ao postar mensagem: " + e.getMessage());
        }
    }
}