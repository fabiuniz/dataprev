package com.dprev.checkout.core.exception;

// Idempotência: mesma chave já em processamento (Redis) ou já persistida (UNIQUE no Postgres).
public class RequisicaoDuplicadaException extends RuntimeException {
    public RequisicaoDuplicadaException(String mensagem) {
        super(mensagem);
    }
}
