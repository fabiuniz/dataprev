package com.tqi.checkout.domain;

// [🟦I] - INTERFACE SEGREGATION PRINCIPLE: Interface focada apenas em estorno.
// Nem todo pagamento é estornável, então segregamos para não obrigar classes a implementarem o que não usam.
public interface Estornavel {
    void estornar(double valor);
}
