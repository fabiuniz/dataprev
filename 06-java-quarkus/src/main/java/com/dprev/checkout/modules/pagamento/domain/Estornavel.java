package com.dprev.checkout.modules.pagamento.domain;

import java.math.BigDecimal;

// [I] ISP: nem todo pagamento é estornável.
public interface Estornavel {
    void estornar(BigDecimal valor);
}
