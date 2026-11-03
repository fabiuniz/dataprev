package com.dprev.checkout.modules.pagamento.domain.strategy;

import com.dprev.checkout.modules.pagamento.domain.MetodoPagamento;
import io.quarkus.logging.Log;
import jakarta.inject.Singleton;
import java.math.BigDecimal;

@Singleton
public class BoletoPagamento implements MetodoPagamento {
    @Override public String codigo() { return "BOLETO"; }

    @Override
    public void processar(BigDecimal valor) {
        Log.infof("📄Boleto: Gerando linha digitável para o valor de R$ %s", valor);
    }
}
