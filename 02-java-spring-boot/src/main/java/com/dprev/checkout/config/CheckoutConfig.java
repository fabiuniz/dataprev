package com.dprev.checkout.config;

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

    // O Spring já gerencia o PagamentoPostgresRepository automaticamente via @Repository.
    // Aqui nós criamos APENAS o Decorator do Redis, injetando o Postgres nele:
    @Bean
    @Primary 
    public PagamentoRepository pagamentoRepository(
            @Qualifier("pagamentoPostgresRepository") PagamentoRepository postgresRepo, 
            IdempotencyKeyGenerator idempotencyKeyGenerator) {
        
        return new PagamentoIdempotenteRedisRepository(postgresRepo, idempotencyKeyGenerator);
    }
}