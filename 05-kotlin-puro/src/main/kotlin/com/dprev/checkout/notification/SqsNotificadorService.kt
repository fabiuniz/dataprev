package com.dprev.checkout.notification

import com.dprev.checkout.service.Notificador

public class SqsNotificadorService : Notificador {
    public override fun enviarComprovante(destino: String, valor: Double) {
        println("[AWS SQS] Publicando mensagem de confirmação para $destino no valor de R$ $valor")
    }
}
