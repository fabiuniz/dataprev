package com.dprev.checkout.modules.pagamento.service;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

// [S] SRP: apenas gera chaves de idempotência.
@ApplicationScoped
public class IdempotencyKeyGenerator {

    public String gerarChaveAleatoria() {
        return UUID.randomUUID().toString();
    }

    public String gerarChaveDeterministica(String emailCliente, String valor) {
        return UUID.nameUUIDFromBytes((emailCliente + ":" + valor).getBytes()).toString();
    }
}
