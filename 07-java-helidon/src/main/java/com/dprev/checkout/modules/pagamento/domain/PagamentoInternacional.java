package com.dprev.checkout.modules.pagamento.domain;

// Cascata de interfaces: quem processa internacional também deve saber estornar.
public interface PagamentoInternacional extends Estornavel {
    void processarTransacaoInternacional(String moedaEstrangeira);
}
