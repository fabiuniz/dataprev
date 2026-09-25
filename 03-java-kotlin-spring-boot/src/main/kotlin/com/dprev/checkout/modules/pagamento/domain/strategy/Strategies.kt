// ❌ Não praticar essa estrutura para manter compatibilidade mútua entre Java/Spring e Kotlin.

package com.dprev.checkout.modules.pagamento.domain.strategy

import com.dprev.checkout.modules.pagamento.domain.MetodoPagamento
import com.dprev.checkout.modules.pagamento.domain.Estornavel
import org.springframework.stereotype.Component

// [🟩O] - OPEN-CLOSED PRINCIPLE & COMPARAÇÃO JAVA: O padrão Strategy permite adicionar novas formas de pagamento 
// de maneira plugável sem alterar as classes existentes. O Kotlin permite agrupar essas pequenas classes em um único arquivo, reduzindo o boilerplate do Java.
// KOTLIN REVOLUTION: Agrupamos os pequenos arquivos do padrão Strategy aqui.
// A sintaxe limpa do Kotlin reduz cada classe a apenas algumas linhas legíveis.

@Component
public class xBoletoPagamento : MetodoPagamento {
    public override fun processar(valor: Double) {
        println("Boleto: Gerando linha digitável para o valor de R$ $valor")
    }
}

@Component
public class xCartaoCreditoPagamento : MetodoPagamento, Estornavel {
    public override fun processar(valor: Double) {
        println("Cartão: Transacionando R$ $valor na operadora.")
    }
    override fun estornar(valor: Double) {
        println("Cartão: Estornando R$ $valor na fatura.")
    }
}

@Component
public class xPixPagamento : MetodoPagamento {
    public override fun processar(valor: Double) {
        println("PIX: Gerando QR Code no valor de R$ $valor")
    }
}

@Component
public class xValeRefeicaoPagamento : MetodoPagamento {
    public override fun processar(valor: Double) {
        // KOTLIN REVOLUTION: O uso de 'require' substitui estruturas 'if-throw' verbosas do Java.
        // É uma função padrão do Kotlin para validação de argumentos de forma limpa.
        require(valor <= 300.0) { "Limite de transacao VR excedido!" }
        println("VR: Processando R$ $valor via cartao beneficio.")
    }
}
