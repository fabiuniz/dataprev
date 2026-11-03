package com.dprev.checkout.modules.pagamento.domain.strategy;

import com.dprev.checkout.modules.pagamento.domain.MetodoPagamento;
import jakarta.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.logging.Logger;

@ApplicationScoped
public class PixPagamento implements MetodoPagamento {
    private static final Logger LOG = Logger.getLogger(PixPagamento.class.getName());

    @Override public String codigo() { return "PIX"; }

    @Override
    public void processar(BigDecimal valor) {
        LOG.info(String.format("⚡PIX: Gerando QR Code no valor de R$ %s", valor));
    }
}