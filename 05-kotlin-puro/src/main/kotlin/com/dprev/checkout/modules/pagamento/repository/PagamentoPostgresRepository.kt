package com.dprev.checkout.modules.pagamento.repository

public class PagamentoPostgresRepository : PagamentoRepository {
    public override fun salvar(email: String, valor: Double, metodo: String) {
        println("[Postgres] Persistindo transação relacional para $email")
    }
}
