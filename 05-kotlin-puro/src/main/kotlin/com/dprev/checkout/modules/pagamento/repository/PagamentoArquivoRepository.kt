package com.dprev.checkout.modules.pagamento.repository

public class PagamentoArquivoRepository : PagamentoRepository {
    public override fun salvar(email: String, valor: Double, metodo: String) {
        println("[Arquivo] Escrevendo log de pagamento em disco para $email")
    }
}
