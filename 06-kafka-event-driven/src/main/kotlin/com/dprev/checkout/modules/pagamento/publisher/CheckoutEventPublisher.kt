package com.dprev.checkout.modules.pagamento.publisher

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class CheckoutEventPublisher(private val kafkaTemplate: KafkaTemplate<String, String>) {

    fun publicar(transactionId: String, payloadJson: String) {
        kafkaTemplate.send("checkout-realizado-topic", transactionId, payloadJson)
            .whenComplete { result, ex ->
                if (ex == null) {
                    println("✅ [KAFKA PUBLISHER] Evento $transactionId enviado na partição ${result.recordMetadata.partition()}")
                } else {
                    println("❌ [KAFKA PUBLISHER] Erro ao publicar evento: ${ex.message}")
                }
            }
    }
}