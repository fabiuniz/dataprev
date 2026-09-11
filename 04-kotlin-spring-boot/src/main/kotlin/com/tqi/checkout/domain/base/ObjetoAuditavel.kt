package com.tqi.checkout.domain.base

// [🟥S] - SINGLE RESPONSIBILITY PRINCIPLE: Possui uma única razão para mudar: a lógica de auditoria.
// [🟩O] - OPEN-CLOSED PRINCIPLE & COMPARAÇÃO KOTLIN: Por padrão em Java, toda classe é aberta (open) para 
// extensão, permitindo que seja a classe base na hierarquia, ao contrário do Kotlin que exige o modificador 'open'.
// COMPARAÇÃO COM JAVA: No Java, as classes são abertas por padrão. No Kotlin, a filosofia inverte:
// todas as classes são 'final' (fechadas). Por isso, precisamos usar explicitamente a palavra-chave 'open'.
open public class ObjetoAuditavel {
    open public fun generateLogAuditoria(valor:Double) {
        System.out.println("[LOG AUDITORIA] Transação registrada no valor de R$ $valor")
    }
}
