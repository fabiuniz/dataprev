package com.dprev.checkout.service

import com.sun.net.httpserver.HttpServer
import com.sun.net.httpserver.HttpExchange
import java.net.InetSocketAddress
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path

object WebServer {
    fun iniciar(porta: Int = 8080) {
        val server = HttpServer.create(InetSocketAddress("0.0.0.0", porta), 0)
        
        // 1. Rota para servir a interface HTML na raiz
        server.createContext("/") { exchange: HttpExchange ->
            val file = Path.of("public/index.html")
            val response = if (Files.exists(file)) {
                Files.readString(file)
            } else {
                "<h1>Página index.html não encontrada na pasta public/</h1>"
            }
            
            val bytes = response.toByteArray(StandardCharsets.UTF_8)
            exchange.responseHeaders.add("Content-Type", "text/html; charset=UTF-8")
            exchange.sendResponseHeaders(200, bytes.size.toLong())
            exchange.responseBody.use { output ->
                output.write(bytes)
            }
        }

        // 2. Rota para processar o Checkout via POST vindo do front-end
        server.createContext("/api/checkout") { exchange: HttpExchange ->
            // Configurar CORS
            exchange.responseHeaders.add("Access-Control-Allow-Origin", "*")
            exchange.responseHeaders.add("Access-Control-Allow-Methods", "POST, OPTIONS")
            exchange.responseHeaders.add("Access-Control-Allow-Headers", "Content-Type")

            if (exchange.requestMethod.equals("OPTIONS", ignoreCase = true)) {
                exchange.sendResponseHeaders(204, -1)
                return@createContext
            }

            if (!exchange.requestMethod.equals("POST", ignoreCase = true)) {
                val resposta = "Método não permitido"
                exchange.sendResponseHeaders(405, resposta.length.toLong())
                exchange.responseBody.use { it.write(resposta.toByteArray()) }
                return@createContext
            }

            try {
                val body = exchange.requestBody.bufferedReader(StandardCharsets.UTF_8).use { it.readText() }
                
                val email = extrairValorJson(body, "email") ?: "cliente@dprev.com"
                val valor = extrairValorJson(body, "valor")?.toDoubleOrNull() ?: 0.0
                val metodoStr = extrairValorJson(body, "metodo") ?: "pix"

                // Instanciação compatível com a sua classe de pagamento original
                val metodoPagamento = when (metodoStr.lowercase()) {
                    "pix" -> com.dprev.checkout.domain.strategy.PixPagamento()
                    "cartao" -> com.dprev.checkout.domain.strategy.CartaoCreditoPagamento() // Construtor padrão original
                    "boleto" -> com.dprev.checkout.domain.strategy.BoletoPagamento()
                    "vr" -> com.dprev.checkout.domain.strategy.ValeRefeicaoPagamento()
                    else -> com.dprev.checkout.domain.strategy.PixPagamento()
                }

                // Executa a regra de negócio do checkout
                val notificador = com.dprev.checkout.service.EmailNotificadorService()
                val checkoutService = CheckoutService(notificador)
                checkoutService.finalizarPedido(valor, metodoPagamento, email)

                val resultado = "Transação de R$ $valor via ${metodoStr.uppercase()} processada com sucesso para $email!"
                val bytes = resultado.toByteArray(StandardCharsets.UTF_8)
                
                exchange.responseHeaders.add("Content-Type", "text/plain; charset=UTF-8")
                exchange.sendResponseHeaders(200, bytes.size.toLong())
                exchange.responseBody.use { it.write(bytes) }

            } catch (e: Exception) {
                val erroMsg = "Erro no checkout: ${e.message}"
                val bytes = erroMsg.toByteArray(StandardCharsets.UTF_8)
                exchange.responseHeaders.add("Content-Type", "text/plain; charset=UTF-8")
                exchange.sendResponseHeaders(400, bytes.size.toLong())
                exchange.responseBody.use { it.write(bytes) }
            }
        }
        
        server.start()
        println("🚀 Servidor Kotlin Puro rodando em http://localhost:$porta")
    }

    private fun extrairValorJson(json: String, chave: String): String? {
        val regex = "\"$chave\"\\s*:\\s*(\"[^\"]*\"|[^,}]+)".toRegex()
        val match = regex.find(json) ?: return null
        return match.groupValues[1].trim('"')
    }
}