package com.tqi.checkout.domain.vr;
import com.tqi.checkout.domain.MetodoPagamento;

// [🟨L] - COMO VIOLAR O LISKOV SUBSTITUTION PRINCIPLE (CONCEITO VISUAL):
// O VR implementa MetodoPagamento, mas força um comportamento inesperado (Exceção por valor) que a interface mãe não previa.
// Isso obriga o código cliente a se adaptar à classe filha, quebrando a substituição harmônica.
public class ValeRefeicaoPagamento implements MetodoPagamento {
    @Override
    public void processar(double valor) {
        // Substituindo o 'require' do Kotlin por validação expressa Java
        if (valor > 300.0) {
            throw new IllegalArgumentException("🚨🍔Limite de transacao VR excedido!");
        }
        System.out.println("🍔VR: Processando R$ " + valor + " via cartao beneficio.");
    }
}
