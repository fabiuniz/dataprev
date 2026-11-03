package com.dprev.checkout.modules.pagamento.domain;

import java.math.BigDecimal;

// [D] DIP: o checkout depende desta abstração.
// [O] OCP: novo meio de pagamento = nova classe @Singleton, sem alterar o CheckoutService.
public interface MetodoPagamento {
    String codigo();
    void processar(BigDecimal valor);
}
