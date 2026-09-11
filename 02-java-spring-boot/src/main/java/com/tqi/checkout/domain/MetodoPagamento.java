package com.tqi.checkout.domain;

// [🟦I] - INTERFACE SEGREGATION PRINCIPLE & COMPARAÇÃO JAVA: Interfaces pequenas e coesas. 
// Evita forçar clientes a dependerem de métodos que não utilizam. O Kotlin permite agrupar todas 
// essas definições em um único arquivo, reduzindo drasticamente a quantidade de arquivos físicos necessários no projeto.
// KOTLIN REVOLUTION: Multi-file agrupado. O Kotlin permite colocar várias interfaces/classes
// correlacionadas no mesmo arquivo físico se fizer sentido para o design. Menos arquivos inúteis.

public interface MetodoPagamento {
    public void processar(double valor);
}

