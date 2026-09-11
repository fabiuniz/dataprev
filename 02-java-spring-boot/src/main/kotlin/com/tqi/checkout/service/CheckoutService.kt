package com.tqi.checkout.service
import com.tqi.checkout.domain.MetodoPagamento
import org.springframework.stereotype.Service
@Service
class CheckoutService {
    fun finalizarPedido(valor: Double, forma: MetodoPagamento) { forma.processar(valor) }
}
