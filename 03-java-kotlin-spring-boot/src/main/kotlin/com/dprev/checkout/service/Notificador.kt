// [🟦I] - INTERFACE SEGREGATION PRINCIPLE: Contrato enxuto e de propósito único. 
// Garante que qualquer serviço de notificação implemente apenas a capacidade de enviar o comprovante.
// COMPARAÇÃO COM JAVA: No Kotlin, omitir modificadores de visibilidade torna a interface 'public' por padrão.
package com.dprev.checkout.service

public interface Notificador {
    public fun enviarComprovante(destino: String, valor: Double)
}
