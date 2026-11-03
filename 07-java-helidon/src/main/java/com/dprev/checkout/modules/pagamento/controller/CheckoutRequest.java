package com.dprev.checkout.modules.pagamento.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record CheckoutRequest(
        @NotBlank @Email String email,
        @NotNull @Positive BigDecimal valor,
        @NotBlank String metodo) {
}
