package com.dprev.checkout.service;

// [🟥S] - SINGLE RESPONSIBILITY PRINCIPLE: Esta classe serve APENAS para gerenciar o canal de notificações.
public class NotificadorService {
    public void enviarComprovante(String email, double valor) {
        System.out.println("E-mail enviado para " + email + " confirmando os R$ " + valor);
    }
}
