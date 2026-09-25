package com.dprev.checkout.modules.pagamento.service

//import org.springframework.stereotype.Service
// [🟥S] - SINGLE RESPONSIBILITY PRINCIPLE: Sua única razão para mudar é a regra de envio específica do canal de E-mails.
// COMPARAÇÃO COM JAVA: Implementa a interface 'Notificador' usando o operador ':'. 
// O Kotlin utiliza 'String Interpolation' nativa ($email, $valor) eliminando a concatenação manual com '+'.
//@Service
public class EmailNotificadorService : Notificador {
    public override fun enviarComprovante(destino: String, valor: Double) {
        // KOTLIN REVOLUTION: String Interpolation nativa ($email, $valor).
        val email = destino // <--- Aqui pegamos o argumento 'destino' que veio da interface
        println("E-mail enviado para $email confirmando os R$ $valor")
    }
}
