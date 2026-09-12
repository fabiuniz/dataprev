// ❌ Não praticar essa estrutura para manter compatibilidade mútua entre Java/Spring e Kotlin.

package com.dprev.checkout.domain.strategy;

import com.dprev.checkout.domain.MetodoPagamento;
import org.springframework.stereotype.Component;

// [🟩O] - OPEN-CLOSED PRINCIPLE & COMPARAÇÃO JAVA: O padrão Strategy permite adicionar novas formas de pagamento 
// de maneira plugável sem alterar as classes existentes. O Kotlin permite agrupar essas pequenas classes em um único arquivo, reduzindo o boilerplate do Java.
// KOTLIN REVOLUTION: Agrupamos os pequenos arquivos do padrão Strategy aqui.
// A sintaxe limpa do Kotlin reduz cada classe a apenas algumas linhas legíveis.


@Component
public class ValeRefeicaoPagamento implements MetodoPagamento {
    @Override
    public void processar(double valor) {
        // KOTLIN REVOLUTION: O uso de 'require' substitui estruturas 'if-throw' verbosas do Java.
        // É uma função padrão do Kotlin para validação de argumentos de forma limpa.
        if (valor > 300.0) {
            throw new IllegalArgumentException("Limite de transacao VR excedido!");
        }
        System.out.println("VR: Processando R$" +valor+" via cartao beneficio.");
    }
}
