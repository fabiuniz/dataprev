package com.dprev.checkout.modules.pagamento.controller

import com.dprev.checkout.modules.pagamento.service.CheckoutService
import com.dprev.checkout.modules.pagamento.domain.MetodoPagamento
import com.dprev.checkout.modules.pagamento.domain.strategy.PixPagamento
import com.dprev.checkout.modules.pagamento.domain.strategy.CartaoCreditoPagamento
import com.dprev.checkout.modules.pagamento.domain.strategy.BoletoPagamento
import com.dprev.checkout.modules.pagamento.domain.strategy.ValeRefeicaoPagamento
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

data class CheckoutRequest(
    val email: String,
    val valor: Double,
    val metodo: String
)

@RestController
@RequestMapping("/api/checkout")
class CheckoutController(
    private val checkoutService: CheckoutService
) {

    @PostMapping
    fun finalizarPedido(@RequestBody request: CheckoutRequest): ResponseEntity<String> {
        return try {
            val metodoPagamento: MetodoPagamento = when (request.metodo.lowercase()) {
                "pix" -> PixPagamento()
                "cartao" -> CartaoCreditoPagamento()
                "boleto" -> BoletoPagamento()
                "vr" -> ValeRefeicaoPagamento()
                else -> throw IllegalArgumentException("Método de pagamento desconhecido: ${request.metodo}")
            }

            checkoutService.finalizarPedido(request.valor, metodoPagamento, request.email)
            ResponseEntity.ok("Pedido processado com sucesso para ${request.email} no valor de R$ ${request.valor} via ${request.metodo.uppercase()}")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message ?: "Erro desconhecido no processamento")
        }
    }
}