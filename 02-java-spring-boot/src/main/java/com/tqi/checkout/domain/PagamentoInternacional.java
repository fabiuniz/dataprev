package com.tqi.checkout.domain;

// [🟩O] - OPEN-CLOSED PRINCIPLE: A interface 'Estornavel' é estendida para suportar transações 
// internacionais sem que o contrato original de estorno precise ser modificado ou poluído.
public interface PagamentoInternacional extends Estornavel {
    public void processarTransacaoInternacional(String moedaEstrangeira);
}
