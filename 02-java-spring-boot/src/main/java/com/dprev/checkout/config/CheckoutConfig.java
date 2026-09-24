package com.dprev.checkout.config;

import com.dprev.checkout.repository.PagamentoIdempotenteRedisRepository;
import com.dprev.checkout.repository.PagamentoPostgresRepository;
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

    // 1. Cria o banco puro com um nome específico
    @Bean
    @Qualifier("bancoPuro")
    public PagamentoRepository pagamentoPostgresRepository() {
        return new PagamentoPostgresRepository();
    }

    // 2. Cria o Redis injetando explicitamente o banco puro pelo nome (@Qualifier)
    @Bean
    @Primary
    public PagamentoRepository pagamentoRepository(
            @Qualifier("bancoPuro") PagamentoRepository pagamentoPostgresRepository, 
            IdempotencyKeyGenerator idempotencyKeyGenerator) {
        
        return new PagamentoIdempotenteRedisRepository(pagamentoPostgresRepository, idempotencyKeyGenerator);
    }
}