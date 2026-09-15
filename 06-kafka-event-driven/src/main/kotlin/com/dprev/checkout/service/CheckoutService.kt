package com.dprev.checkout.service

import com.dprev.checkout.domain.CheckoutRequest
import com.dprev.checkout.publisher.CheckoutEventPublisher
import org.springframework.stereotype.Service

@Service
class CheckoutService(private val eventPublisher: CheckoutEventPublisher) {

    fun processarCheckout(request: CheckoutRequest) {
        // Simula regra de negócio local
        val payloadJson = "{\"transactionId\":\"${request.transactionId}\", \"cpf\":\"${request.cpfCliente}\"}"
        eventPublisher.publicar(request.transactionId, payloadJson)
    }
}