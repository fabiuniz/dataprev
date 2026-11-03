package com.dprev.checkout.core.config;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class CheckoutConfig {

    @ConfigProperty(name = "checkout.vr.limite", defaultValue = "300")
    double vrLimite;

    @ConfigProperty(name = "checkout.idempotencia.ttl-segundos", defaultValue = "10")
    int ttlSegundos;

    @ConfigProperty(name = "checkout.sqs.habilitado", defaultValue = "true")
    boolean sqsHabilitado;

    @ConfigProperty(name = "checkout.sqs.endpoint", defaultValue = "http://localhost:4566")
    String sqsEndpoint;

    @ConfigProperty(name = "checkout.sqs.region", defaultValue = "us-east-1")
    String sqsRegion;

    @ConfigProperty(name = "checkout.sqs.fila", defaultValue = "fila-notificacao-checkout")
    String sqsFila;

    public Vr vr() { return new Vr(vrLimite); }
    public Idempotencia idempotencia() { return new Idempotencia(ttlSegundos); }
    public Sqs sqs() { return new Sqs(sqsHabilitado, sqsEndpoint, sqsRegion, sqsFila); }

    public record Vr(double limite) {}
    public record Idempotencia(int ttlSegundos) {}
    public record Sqs(boolean habilitado, String endpoint, String region, String fila) {}
}