package com.dprev.checkout.notification;
import org.springframework.stereotype.Service;

// [🟥S] - SINGLE RESPONSIBILITY PRINCIPLE: Esta classe serve APENAS para gerenciar o canal específico de E-mails.
@Service
public class EmailNotificadorService implements Notificador {
    @Override
    public void enviarComprovante(String email, double valor) {
        // Sem interpolação nativa $, voltamos à concatenação tradicional do Java
        System.out.println("✉️E-mail enviado para " + email + " confirmando os R$ " + valor);
    }
}
