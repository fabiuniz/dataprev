package com.dprev.checkout.modules.pagamento.repository

public interface PagamentoRepository {
    public fun salvar(email: String, valor: Double, metodo: String)
}
