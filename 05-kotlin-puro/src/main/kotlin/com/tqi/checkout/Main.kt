@file:JvmName("Application")

package com.tqi.checkout

import com.tqi.checkout.service.CheckoutService
import com.tqi.checkout.service.EmailNotificadorService
import com.tqi.checkout.domain.strategy.*
import com.tqi.checkout.domain.cascade.CartaoCorporativoPremium

fun main() {
    // 1. Instancia manualmente as dependências de infraestrutura
    val notificador = EmailNotificadorService()

    // 2. Injeta o notificador no serviço de checkout (Injeção manual)
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