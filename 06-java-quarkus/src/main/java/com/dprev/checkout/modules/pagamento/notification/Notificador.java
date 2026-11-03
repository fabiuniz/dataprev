package com.dprev.checkout.modules.pagamento.notification;

import java.math.BigDecimal;

// [D] DIP: o checkout não sabe se o aviso vai por SQS, e-mail ou pombo-correio.
public interface Notificador {
    void enviarComprovante(String destino, BigDecimal valor);
}
