package com.dprev.checkout;

import com.dprev.checkout.service.CheckoutService;
import com.dprev.checkout.service.WebServer;
import com.dprev.checkout.notification.EmailNotificadorService;
import com.dprev.checkout.repository.PagamentoRepository;
import com.dprev.checkout.notification.Notificador;
import com.dprev.checkout.repository.PagamentoPostgresRepository;
import com.dprev.checkout.repository.PagamentoIdempotenteRedisRepository;
import com.dprev.checkout.service.IdempotencyKeyGenerator;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n🚀 ========================================================");
        System.out.println("🚀 INICIALIZANDO ECOSSISTEMA FULL-STACK NATIVO");
        System.out.println("🚀 ========================================================");

        // 1. Injeção de dependências corporativa manual
        Notificador notificador = new com.dprev.checkout.notification.SqsNotificadorService();
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