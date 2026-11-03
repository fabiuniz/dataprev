package com.dprev.checkout.modules.pagamento.domain.strategy;

import com.dprev.checkout.modules.pagamento.domain.Estornavel;
import com.dprev.checkout.modules.pagamento.domain.MetodoPagamento;
import io.quarkus.logging.Log;
import jakarta.inject.Singleton;
import java.math.BigDecimal;

// [I] ISP: implementa pagamento E estorno de forma independente.
@Singleton
public class CartaoCreditoPagamento implements MetodoPagamento, Estornavel {
    @Override public String codigo() { return "CARTAO"; }

    @Override
    public void processar(BigDecimal valor) {
        Log.infof("💳Cartão: Transacionando R$ %s na operadora.", valor);
    }

    @Override
    public void estornar(BigDecimal valor) {
        Log.infof("🚨💳Cartão: Estornando R$ %s na fatura.", valor);
    }
}
