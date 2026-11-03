package com.dprev.checkout.modules.pagamento.domain.strategy;

import com.dprev.checkout.core.config.CheckoutConfig;
import com.dprev.checkout.modules.pagamento.domain.MetodoPagamento;
import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.logging.Logger;

@ApplicationScoped
public class ValeRefeicaoPagamento implements MetodoPagamento {
    private static final Logger LOG = Logger.getLogger(ValeRefeicaoPagamento.class.getName());
    private final BigDecimal limite;

    @Inject
    public ValeRefeicaoPagamento(CheckoutConfig config) {
        this(config.vr().limite());
    }

    public ValeRefeicaoPagamento(double limite) {
        this.limite = BigDecimal.valueOf(limite);
    }

    @Override public String codigo() { return "VR"; }

    @Override
    public void processar(BigDecimal valor) {
        if (valor.compareTo(limite) > 0) {
            throw new IllegalArgumentException("Limite de transacao VR excedido!");
        }
        LOG.info(String.format("🍔VR: Processando R$ %s via cartao beneficio.", valor));
    }
}