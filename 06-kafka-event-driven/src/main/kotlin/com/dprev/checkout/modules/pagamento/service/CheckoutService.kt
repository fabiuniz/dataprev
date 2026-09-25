package com.dprev.checkout.modules.pagamento.service

import com.dprev.checkout.modules.pagamento.domain.CheckoutRequest
import com.dprev.checkout.modules.pagamento.publisher.CheckoutEventPublisher
import org.springframework.stereotype.Service

@Service
class CheckoutService(private val eventPublisher: CheckoutEventPublisher) {

    fun processarCheckout(request: CheckoutRequest) {
        // Simula regra de negócio local
        val payloadJson = "{\"transactionId\":\"${request.transactionId}\", \"cpf\":\"${request.cpfCliente}\"}"
        eventPublisher.publicar(request.transactionId, payloadJson)
    }
}