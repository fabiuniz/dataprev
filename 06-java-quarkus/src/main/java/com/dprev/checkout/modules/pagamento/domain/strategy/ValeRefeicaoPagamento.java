package com.dprev.checkout.modules.pagamento.domain.strategy;

import com.dprev.checkout.core.config.CheckoutConfig;
import com.dprev.checkout.modules.pagamento.domain.MetodoPagamento;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.math.BigDecimal;

// [L] COMO VIOLAR O LSP: a interface não prevê exceção por valor, mas o VR lança uma.
// O cliente (CheckoutService) passa a precisar conhecer essa regra escondida.
@Singleton
public class ValeRefeicaoPagamento implements MetodoPagamento {

    private final BigDecimal limite;

    @Inject
    public ValeRefeicaoPagamento(CheckoutConfig config) {
        this(config.vr().limite());
    }

    // Construtor usado nos testes unitários (sem container CDI)
    public ValeRefeicaoPagamento(double limite) {
        this.limite = BigDecimal.valueOf(limite);
    }

    @Override public String codigo() { return "VR"; }

    @Override
    public void processar(BigDecimal valor) {
        if (valor.compareTo(limite) > 0) {
            throw new IllegalArgumentException("Limite de transacao VR excedido!");
        }
        Log.infof("🍔VR: Processando R$ %s via cartao beneficio.", valor);
    }
}
