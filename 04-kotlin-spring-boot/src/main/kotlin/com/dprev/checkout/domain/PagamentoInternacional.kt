package com.dprev.checkout.domain

// [🟩O] - OPEN-CLOSED PRINCIPLE: A interface 'Estornavel' é estendida para suportar transações 
// internacionais sem que o contrato original de estorno precise ser modificado ou poluído.
public interface PagamentoInternacional : Estornavel {
    public fun processarTransacaoInternacional(moedaEstrangeira : String )
}
