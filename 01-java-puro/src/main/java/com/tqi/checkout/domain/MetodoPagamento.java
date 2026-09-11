package com.tqi.checkout.domain;

// [🟪D] - DEPENDENCY INVERSION PRINCIPLE: Abstração de alto nível para o checkout depender de contratos.
// [🟩O] - OPEN/CLOSED PRINCIPLE: Permite estender o sistema com novas formas de pagamento.
public interface MetodoPagamento {
    void processar(double valor);
}
