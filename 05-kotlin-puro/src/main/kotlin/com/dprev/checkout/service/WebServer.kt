package com.dprev.checkout.service

import com.sun.net.httpserver.HttpServer
import com.sun.net.httpserver.HttpExchange
import java.net.InetSocketAddress
import java.nio.file.Files
import java.nio.file.Path

object WebServer {
    fun iniciar(porta: Int = 8080) {
        val server = HttpServer.create(InetSocketAddress("0.0.0.0", porta), 0)
        
        server.createContext("/") { exchange: HttpExchange ->
            val file = Path.of("public/index.html")
            val response = if (Files.exists(file)) {
                Files.readString(file)
            } else {
                "<h1>Página não encontrada no Kotlin Puro!</h1>"
            }
            
            val bytes = response.toByteArray()
            exchange.sendResponseHeaders(200, bytes.size.toLong())
            exchange.responseBody.use { output ->
                output.write(bytes)
            }
        }
        
        server.start()
        println("🚀 Servidor Kotlin Puro rodando em http://localhost:$porta")
    }
}