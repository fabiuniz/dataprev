package com.dprev.checkout.modules.pagamento.notification;

// [🟪D] - DEPENDENCY INVERSION PRINCIPLE: Interface de alto nível que blinda o CheckoutService.
// Agora o checkout não sabe se o aviso vai por e-mail, SMS ou pombo correio. Ele só depende do contrato.
public interface Notificador {
    void enviarComprovante(String destino, double valor);
}
