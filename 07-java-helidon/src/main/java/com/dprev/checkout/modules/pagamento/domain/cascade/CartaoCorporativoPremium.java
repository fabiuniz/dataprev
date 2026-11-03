package com.dprev.checkout.modules.pagamento.domain.cascade;

import com.dprev.checkout.modules.pagamento.domain.PagamentoInternacional;
import com.dprev.checkout.modules.pagamento.domain.base.CartaoBase;
import jakarta.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.logging.Logger;

@ApplicationScoped
public class CartaoCorporativoPremium extends CartaoBase implements PagamentoInternacional {
    private static final Logger LOG = Logger.getLogger(CartaoCorporativoPremium.class.getName());

    @Override
    public String codigo() { return "CORPORATIVO"; }

    @Override
    public void processarTransacaoInternacional(String moedaEstrangeira) {
        LOG.info(String.format("👑Corporativo Premium: Convertendo taxa para %s", moedaEstrangeira));
    }

    @Override
    public void estornar(BigDecimal valor) {
        LOG.info(String.format("🚨👑Corporativo Premium: Estornando teto de R$ %s com prioridade.", valor));
    }
}