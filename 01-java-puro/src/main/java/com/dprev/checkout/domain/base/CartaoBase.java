package com.dprev.checkout.domain.base;
import com.dprev.checkout.domain.MetodoPagamento;

// CASCATA DE EXTENDS (Mãe): Puxa os logs de segurança de 'ObjetoAuditavel' para dentro.
// [🟨L] - LISKOV SUBSTITUTION PRINCIPLE: Ela implementa 'MetodoPagamento' de forma genérica e segura.
public class CartaoBase extends ObjetoAuditavel implements MetodoPagamento {
    protected String bandeira = "VISA";

    @Override
    public void processar(double valor) {
        System.out.println("🧱Cartão Base: Processando R$ " + valor + " na bandeira " + bandeira);
    }
}
