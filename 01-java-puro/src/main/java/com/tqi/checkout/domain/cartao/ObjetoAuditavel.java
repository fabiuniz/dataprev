package com.tqi.checkout.domain.cartao;
// Em Java, as classes já são abertas (open) por padrão para herança
// [🟥S] - SINGLE RESPONSIBILITY PRINCIPLE: Esta classe cuida UNICAMENTE de logs e auditoria.
public class ObjetoAuditavel {
    public void generateLogAuditoria(double valor) {
        System.out.println("📝[LOG AUDITORIA] Transação registrada no valor de R$ " + valor);
    }
}
