package com.dprev.checkout.modules.pagamento.domain.base;

import com.dprev.checkout.modules.pagamento.domain.MetodoPagamento;
import java.math.BigDecimal;
import java.util.logging.Logger;

public abstract class CartaoBase extends ObjetoAuditavel implements MetodoPagamento {
    private static final Logger LOG = Logger.getLogger(CartaoBase.class.getName());
    protected String bandeira = "VISA";

    @Override
    public void processar(BigDecimal valor) {
        LOG.info(String.format("🧱Cartão Base: Processando R$ %s na bandeira %s", valor, bandeira));
    }
}