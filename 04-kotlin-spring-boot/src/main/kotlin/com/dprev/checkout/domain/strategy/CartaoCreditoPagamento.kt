// ❌ Não praticar essa estrutura para manter compatibilidade mútua entre Java/Spring e Kotlin.

package com.dprev.checkout.domain.strategy

import com.dprev.checkout.domain.MetodoPagamento
import com.dprev.checkout.domain.Estornavel
import org.springframework.stereotype.Component

// [🟩O] - OPEN-CLOSED PRINCIPLE & COMPARAÇÃO JAVA: O padrão Strategy permite adicionar novas formas de pagamento 
// de maneira plugável sem alterar as classes existentes. O Kotlin permite agrupar essas pequenas classes em um único arquivo, reduzindo o boilerplate do Java.
// KOTLIN REVOLUTION: Agrupamos os pequenos arquivos do padrão Strategy aqui.
// A sintaxe limpa do Kotlin reduz cada classe a apenas algumas linhas legíveis.

@Component
public class CartaoCreditoPagamento : MetodoPagamento, Estornavel {
    //@Override
    public override fun processar(valor: Double ) {
        println("Cartão: Transacionando R$ $valor na operadora.")
    }
    //@Override
    public override fun estornar(valor : Double) {
        println("Cartão: Estornando R$ "+valor+" na fatura.")
    }
}
