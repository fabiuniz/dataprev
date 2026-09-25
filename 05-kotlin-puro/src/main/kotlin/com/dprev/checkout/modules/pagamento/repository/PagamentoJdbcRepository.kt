package com.dprev.checkout.modules.pagamento.repository

public class PagamentoJdbcRepository : PagamentoRepository {
    public override fun salvar(email: String, valor: Double, metodo: String) {
        println("[JDBC] Salvando pagamento de $email no valor de R$ $valor via $metodo")
    }
}
