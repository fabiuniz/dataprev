package com.tqi.checkout.domain.cartao;

import com.tqi.checkout.domain.cartao.CartaoBase;
import com.tqi.checkout.domain.PagamentoInternacional;

// O APOGEU DA CASCATA DE REUTILIZAÇÃO E CONTRATOS:
// [🟩O] - OCP: Estendemos o sistema com um cartão premium internacional sem tocar em nenhuma linha antiga.
// [🟨L] - LSP: Como herda de 'CartaoBase', substitui perfeitamente qualquer 'MetodoPagamento' na esteira principal.
// [🟦I] - ISP: Implementa 'PagamentoInternacional' que traz a obrigatoriedade do estorno sem inchar o código.
public class CartaoCorporativoPremium extends CartaoBase implements PagamentoInternacional {

    // Herda o método processar() (puxou para dentro) automaticamente da classe mãe CartaoBase.

    @Override
    public void processarTransacaoInternacional(String moedaEstrangeira) {
        System.out.println("👑Corporativo Premium: Convertendo taxa para " + moedaEstrangeira);
    }

    @Override
    public void estornar(double valor) {
        System.out.println("🚨👑Corporativo Premium: Estornando teto de R$ " + valor + " com prioridade.");
    }
}
