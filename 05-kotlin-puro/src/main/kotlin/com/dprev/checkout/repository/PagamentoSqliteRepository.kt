package com.dprev.checkout.repository

public class PagamentoSqliteRepository : PagamentoRepository {
    public override fun salvar(email: String, valor: Double, metodo: String) {
        println("[SQLite] Gravando transação localmente para $email")
    }
}
