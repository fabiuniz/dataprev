package com.dprev.checkout.modules.pagamento.listener

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class CheckoutEventListener {

    @KafkaListener(topics = ["checkout-realizado-topic"], groupId = "checkout-group")
    fun consumir(mensagem: String) {
        println("📨 [KAFKA LISTENER] Mensagem recebida no consumer group: $mensagem")
    }
}