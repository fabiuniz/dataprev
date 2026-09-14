package com.dprev.checkout.repository

public interface PagamentoRepository {
    public fun salvar(email: String, valor: Double, metodo: String)
}
