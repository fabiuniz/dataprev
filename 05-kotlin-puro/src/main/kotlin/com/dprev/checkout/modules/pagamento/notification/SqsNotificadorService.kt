package com.dprev.checkout.modules.pagamento.notification

import com.dprev.checkout.modules.pagamento.service.Notificador

public class SqsNotificadorService : Notificador {
    public override fun enviarComprovante(destino: String, valor: Double) {
        println("[AWS SQS] Publicando mensagem de confirmação para $destino no valor de R$ $valor")
    }
}
