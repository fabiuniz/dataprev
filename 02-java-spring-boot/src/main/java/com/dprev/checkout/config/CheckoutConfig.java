package com.dprev.checkout.config;

import com.dprev.checkout.repository.PagamentoPostgresRepository;
import com.dprev.checkout.repository.PagamentoIdempotenteRedisRepository;
import com.dprev.checkout.repository.PagamentoRepository;
import com.dprev.checkout.service.IdempotencyKeyGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CheckoutConfig {

    @Bean
    public IdempotencyKeyGenerator idempotencyKeyGenerator() {
        return new IdempotencyKeyGenerator();
    }

    // 1. Registramos o Postgres explicitamente com um nome/qualificador único
    @Bean
    @Qualifier("postgresRepo")
    public PagamentoRepository pagamentoPostgresRepository() {
        return new PagamentoPostgresRepository();
    }

    // 2. Registramos o Redis (Decorator) injetando o Postgres de forma segura pelo @Qualifier
    @Bean
    @Primary // Diz ao Spring: "Se alguém pedir PagamentoRepository sem especificar, use este!"
    public PagamentoRepository pagamentoRepository(
            @Qualifier("postgresRepo") PagamentoRepository postgresRepo, 
            IdempotencyKeyGenerator idempotencyKeyGenerator) {
        
        return new PagamentoIdempotenteRedisRepository(postgresRepo, idempotencyKeyGenerator);
    }
}