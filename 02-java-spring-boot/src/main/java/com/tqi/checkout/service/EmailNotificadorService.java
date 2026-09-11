package com.tqi.checkout.service;

import org.springframework.stereotype.Service;
// [🟥S] - SINGLE RESPONSIBILITY PRINCIPLE: Sua única razão para mudar é a regra de envio específica do canal de E-mails.
// COMPARAÇÃO COM JAVA: Implementa a interface 'Notificador' usando o operador ':'. 
// O Kotlin utiliza 'String Interpolation' nativa ($email, $valor) eliminando a concatenação manual com '+'.
@Service
public class EmailNotificadorService implements Notificador {
    @Override
    public void enviarComprovante(String email , double valor) {
        // KOTLIN REVOLUTION: String Interpolation nativa ($email, $valor).
        // Chega de concatenações confusas de strings com o operador '+'.
        System.out.println("E-mail enviado para "+ email +" confirmando os R$ "+ valor);
    }
}
