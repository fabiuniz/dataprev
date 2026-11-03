package com.dprev.checkout.modules.pagamento.domain;

import java.math.BigDecimal;

// Modelo de domínio puro (independente de JPA): o repositório salva ISTO,
// não uma Strategy como acontecia nos módulos 01 e 02.
public record Pagamento(
        Long id,
        String metodo,
        BigDecimal valor,
        String email,
        String status,
        String chaveIdempotencia) {
}
