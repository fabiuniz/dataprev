package com.dprev.checkout.modules.pagamento.controller

import com.dprev.checkout.modules.pagamento.domain.MetodoPagamento
import com.dprev.checkout.modules.pagamento.domain.strategy.BoletoPagamento
import com.dprev.checkout.modules.pagamento.domain.strategy.CartaoCreditoPagamento
import com.dprev.checkout.modules.pagamento.domain.strategy.PixPagamento
import com.dprev.checkout.modules.pagamento.domain.strategy.ValeRefeicaoPagamento
import com.dprev.checkout.modules.pagamento.service.CheckoutService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class CheckoutController(
    private val checkoutService: CheckoutService,
    private val pix: PixPagamento,
    private val cartaoCredito: CartaoCreditoPagamento,
    private val boleto: BoletoPagamento,
    private val vr: ValeRefeicaoPagamento
) {

    @PostMapping("/checkout")
    fun finalizarPedido(@RequestBody request: CheckoutRequest): ResponseEntity<String> {
        val estrategia: MetodoPagamento = when (request.metodo.uppercase().trim()) {
            "PIX" -> pix
            "CARTAO", "CREDITO" -> cartaoCredito
            "BOLETO" -> boleto
            "VR", "VALE_REFEICAO" -> vr
            else -> throw IllegalArgumentException("Método de pagamento inválido: ${request.metodo}")
        }

        checkoutService.finalizarPedido(request.valor, estrategia, request.email)
        return ResponseEntity.ok("Checkout finalizado com sucesso via ${request.metodo.uppercase()}!")
    }

    data class CheckoutRequest(
        val email: String,
        val valor: Double,
        val metodo: String
    )
}