package com.dprev.checkout.modules.pagamento.repository

public class PagamentoIdempotenteRedisRepository(private val generator: IdempotencyKeyGenerator) {
    public fun processarComIdempotencia(email: String, valor: Double, acao: () -> Unit) {
        val chave = generator.gerarChave(email, valor)
        println("[Redis] Verificando chave de idempotência: $chave")
        acao()
    }
}
