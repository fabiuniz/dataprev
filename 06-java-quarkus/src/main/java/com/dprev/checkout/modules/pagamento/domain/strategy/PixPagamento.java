package com.dprev.checkout.modules.pagamento.domain.strategy;

import com.dprev.checkout.modules.pagamento.domain.MetodoPagamento;
import io.quarkus.logging.Log;
import jakarta.inject.Singleton;
import java.math.BigDecimal;

@Singleton
public class PixPagamento implements MetodoPagamento {
    @Override public String codigo() { return "PIX"; }

    @Override
    public void processar(BigDecimal valor) {
        Log.infof("⚡PIX: Gerando QR Code no valor de R$ %s", valor);
    }
}
