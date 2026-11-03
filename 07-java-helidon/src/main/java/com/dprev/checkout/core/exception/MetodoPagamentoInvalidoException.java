package com.dprev.checkout.core.exception;

public class MetodoPagamentoInvalidoException extends RuntimeException {
    public MetodoPagamentoInvalidoException(String metodo) {
        super("Método de pagamento inválido: " + metodo);
    }
}
