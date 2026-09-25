package com.dprev.checkout.modules.pagamento.domain.strategy;
import com.dprev.checkout.modules.pagamento.domain.MetodoPagamento;
import com.dprev.checkout.modules.pagamento.domain.Estornavel;

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
