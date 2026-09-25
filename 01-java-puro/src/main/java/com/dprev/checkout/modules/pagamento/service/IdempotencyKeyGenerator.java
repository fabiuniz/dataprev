package com.dprev.checkout.modules.pagamento.service;

import java.util.UUID;

/**
 * [🟥S] - SINGLE RESPONSIBILITY PRINCIPLE:
 * Gera chaves de idempotência textuais puras, desacopladas do modelo de domínio.
 */
public class IdempotencyKeyGenerator {

    /**
     * Retorna um UUID aleatório como String.
     */
    public String gerarChaveAleatoria() {
        return UUID.randomUUID().toString();
    }

    /**
     * Retorna uma chave estável (determinística) baseada nos dados da requisição.
     */
    public String gerarChaveDeterministica(String emailCliente, double valor) {
        String semente = emailCliente + ":" + valor;
        return UUID.nameUUIDFromBytes(semente.getBytes()).toString();
    }
}