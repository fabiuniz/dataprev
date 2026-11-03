package com.dprev.checkout.modules.pagamento.domain.strategy;

import com.dprev.checkout.modules.pagamento.domain.MetodoPagamento;
import jakarta.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.logging.Logger;

@ApplicationScoped
public class BoletoPagamento implements MetodoPagamento {
    private static final Logger LOG = Logger.getLogger(BoletoPagamento.class.getName());

    @Override public String codigo() { return "BOLETO"; }

    @Override
    public void processar(BigDecimal valor) {
        LOG.info(String.format("📄Boleto: Gerando linha digitável para o valor de R$ %s", valor));
    }
}