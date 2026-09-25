package com.dprev.checkout.modules.pagamento.notification;

// [🟥S] - SINGLE RESPONSIBILITY PRINCIPLE: Esta classe serve APENAS para gerenciar o canal específico de E-mails.
public class EmailNotificadorService implements Notificador {
    @Override
    public void enviarComprovante(String email, double valor) {
        // Sem interpolação nativa $, voltamos à concatenação tradicional do Java
        System.out.println("✉️E-mail enviado para " + email + " confirmando os R$ " + valor);
    }
}
