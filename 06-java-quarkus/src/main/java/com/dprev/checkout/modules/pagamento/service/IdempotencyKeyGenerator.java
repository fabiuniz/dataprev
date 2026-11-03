package com.dprev.checkout.modules.pagamento.service;

import jakarta.inject.Singleton;
import java.util.UUID;

// [S] SRP: apenas gera chaves de idempotência.
@Singleton
public class IdempotencyKeyGenerator {

    public String gerarChaveAleatoria() {
        return UUID.randomUUID().toString();
    }

    public String gerarChaveDeterministica(String emailCliente, String valor) {
        return UUID.nameUUIDFromBytes((emailCliente + ":" + valor).getBytes()).toString();
    }
}
