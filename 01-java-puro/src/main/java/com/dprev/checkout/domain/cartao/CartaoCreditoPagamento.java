package com.dprev.checkout.domain.cartao;
import com.dprev.checkout.domain.MetodoPagamento;
import com.dprev.checkout.domain.Estornavel;

// [🟦I] - Aplicação prática da segregação: Cartão implementa o pagamento E o estorno de forma independente.
public class CartaoCreditoPagamento implements MetodoPagamento, Estornavel {
    @Override
    public void processar(double valor) {
        System.out.println("💳Cartão: Transacionando R$ " + valor + " na operadora.");
    }

    @Override
    public void estornar(double valor) {
        System.out.println("🚨💳Cartão: Estornando R$ " + valor + " na fatura.");
    }
}
