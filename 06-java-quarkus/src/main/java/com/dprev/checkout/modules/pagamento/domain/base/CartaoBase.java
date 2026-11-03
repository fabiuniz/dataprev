package com.dprev.checkout.modules.pagamento.domain.base;

import com.dprev.checkout.modules.pagamento.domain.MetodoPagamento;
import io.quarkus.logging.Log;
import java.math.BigDecimal;

// [L] LSP: contrato genérico e seguro para qualquer subclasse.
public abstract class CartaoBase extends ObjetoAuditavel implements MetodoPagamento {
    protected String bandeira = "VISA";

    @Override
    public void processar(BigDecimal valor) {
        Log.infof("🧱Cartão Base: Processando R$ %s na bandeira %s", valor, bandeira);
    }
}
