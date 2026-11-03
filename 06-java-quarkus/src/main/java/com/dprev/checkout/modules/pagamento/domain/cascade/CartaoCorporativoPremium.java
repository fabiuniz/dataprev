package com.dprev.checkout.modules.pagamento.domain.cascade;

import com.dprev.checkout.modules.pagamento.domain.PagamentoInternacional;
import com.dprev.checkout.modules.pagamento.domain.base.CartaoBase;
import io.quarkus.logging.Log;
import jakarta.inject.Singleton;
import java.math.BigDecimal;

// Cascata: herda CartaoBase (extends) + contrato internacional/estorno (implements).
@Singleton
public class CartaoCorporativoPremium extends CartaoBase implements PagamentoInternacional {

    @Override
    public String codigo() { return "CORPORATIVO"; }

    @Override
    public void processarTransacaoInternacional(String moedaEstrangeira) {
        Log.infof("👑Corporativo Premium: Convertendo taxa para %s", moedaEstrangeira);
    }

    @Override
    public void estornar(BigDecimal valor) {
        Log.infof("🚨👑Corporativo Premium: Estornando teto de R$ %s com prioridade.", valor);
    }
}
