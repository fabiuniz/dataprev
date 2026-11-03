package com.dprev.checkout.modules.pagamento.domain.strategy;

import com.dprev.checkout.modules.pagamento.domain.Estornavel;
import com.dprev.checkout.modules.pagamento.domain.MetodoPagamento;
import jakarta.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.logging.Logger;

@ApplicationScoped
public class CartaoCreditoPagamento implements MetodoPagamento, Estornavel {
    private static final Logger LOG = Logger.getLogger(CartaoCreditoPagamento.class.getName());

    @Override public String codigo() { return "CARTAO"; }

    @Override
    public void processar(BigDecimal valor) {
        LOG.info(String.format("💳Cartão: Transacionando R$ %s na operadora.", valor));
    }

    @Override
    public void estornar(BigDecimal valor) {
        LOG.info(String.format("🚨💳Cartão: Estornando R$ %s na fatura.", valor));
    }
}