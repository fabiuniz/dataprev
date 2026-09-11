package com.tqi.checkout;

import com.tqi.checkout.service.CheckoutService;
import com.tqi.checkout.service.WebServer;
import com.tqi.checkout.notification.EmailNotificadorService;
import com.tqi.checkout.repository.PagamentoRepository;
import com.tqi.checkout.notification.Notificador;
import com.tqi.checkout.repository.PagamentoPostgresRepository;
import com.tqi.checkout.repository.PagamentoIdempotenteRedisRepository;
import com.tqi.checkout.service.IdempotencyKeyGenerator;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n🚀 ========================================================");
        System.out.println("🚀 INICIALIZANDO ECOSSISTEMA FULL-STACK NATIVO");
        System.out.println("🚀 ========================================================");

        // 1. Injeção de dependências corporativa manual
        Notificador notificador = new com.tqi.checkout.notification.SqsNotificadorService();
        PagamentoRepository repository = new PagamentoPostgresRepository();
        IdempotencyKeyGenerator keyGenerator = new IdempotencyKeyGenerator();
        PagamentoRepository repositoryComRedis = new PagamentoIdempotenteRedisRepository(repository, keyGenerator);
        CheckoutService checkoutService = new CheckoutService(notificador, repositoryComRedis);

        // 2. Sobe o servidor HTTP escutando a web na porta 8080
        try {
            WebServer servidor = new WebServer(checkoutService);
            servidor.iniciar(8080);
        } catch (Exception e) {
            System.err.println("❌ Falha crítica ao subir servidor HTTP: " + e.getMessage());
        }
    }
}