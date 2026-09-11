package com.tqi.checkout.domain.strategy
import com.tqi.checkout.domain.MetodoPagamento
import org.springframework.stereotype.Component
@Component
class PixPagamento : MetodoPagamento {
    override fun processar(valor: Double) { println("Spring + Kotlin PIX: R$ $valor") }
}
