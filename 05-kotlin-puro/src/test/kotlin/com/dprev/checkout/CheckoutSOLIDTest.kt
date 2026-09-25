package com.dprev.checkout

import com.dprev.checkout.modules.pagamento.service.CheckoutService
import com.dprev.checkout.modules.pagamento.service.Notificador
import com.dprev.checkout.modules.pagamento.domain.strategy.PixPagamento
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue

class CheckoutSOLIDTest {

    @Test
    fun testCheckoutFluxoPrincipal() {
        val notificadorMock = object : Notificador {
            override fun enviarComprovante(destino: String, valor: Double) {
                println("Mock notificação enviada para $destino")
            }
        }
        
        val service = CheckoutService(notificadorMock)
        val pix = PixPagamento()
        
        service.finalizarPedido(150.0, pix, "teste@dprev.com")
        assertTrue(true)
    }
}
