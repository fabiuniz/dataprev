package com.dprev.checkout.domain;

// [🟦I] - INTERFACE SEGREGATION PRINCIPLE: Segrega transações em moeda estrangeira de forma limpa.
// CASCATA DE IMPLEMENTS: Estende 'Estornavel'. Quem processa internacional também deve saber estornar.
public interface PagamentoInternacional extends Estornavel {
    void processarTransacaoInternacional(String moedaEstrangeira);
}
