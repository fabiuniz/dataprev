package com.tqi.checkout.domain.base;
import com.tqi.checkout.domain.MetodoPagamento;
// HIERARQUIA DE CLASSES (Equivalente ao 'extends' do Java): herda de 'ObjetoAuditavel' para reaproveitar logs de segurança.
// [🟨L] - LISKOV SUBSTITUTION PRINCIPLE: Garante um contrato estável para que qualquer subclasse
// (ex: CartaoCorporativoPremium) possa substituir 'CartaoBase' ou 'MetodoPagamento' sem quebrar o sistema.
// Marcada como 'open' porque em Kotlin as classes são 'final' por padrão (diferente do Java, onde são abertas por padrão).
public class CartaoBase extends ObjetoAuditavel implements MetodoPagamento {
    protected String bandeira = "Visa";
    @Override
    public void processar(double valor) {
        System.out.println("Cartão Base: Processando R$ "+ valor+" na bandeira "+bandeira);
    }
}
