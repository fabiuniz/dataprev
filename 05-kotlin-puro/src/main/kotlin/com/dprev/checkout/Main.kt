@file:JvmName("Application")

package com.dprev.checkout

import com.dprev.checkout.service.WebServer
import com.dprev.checkout.service.CheckoutService
import com.dprev.checkout.service.EmailNotificadorService
import com.dprev.checkout.domain.strategy.*
import com.dprev.checkout.domain.cascade.CartaoCorporativoPremium

fun main() {
    // 1. Sobe o servidor HTTP nativo na porta 8080 permitindo conexões externas
    WebServer.iniciar(8080)

    // 2. Instancia manualmente as dependências de infraestrutura
    val notificador = EmailNotificadorService()
    val checkoutService = CheckoutService(notificador)

    // 3. Instancia as estratégias de pagamento
    val pix = PixPagamento()
    val cartaoCredito = CartaoCreditoPagamento()
    val boleto = BoletoPagamento()
    val vr = ValeRefeicaoPagamento()
    val cartaoCorp = CartaoCorporativoPremium()

    // 4. Executa a lógica de testes diretamente
    val runner = RunnerEstudos(checkoutService, pix, cartaoCredito, boleto, vr, cartaoCorp)
    runner.executar()
}