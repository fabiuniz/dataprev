package com.dprev.checkout.core.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

// Equivalente Quarkus/MicroProfile do @Value / @ConfigurationProperties do Spring.
@ConfigMapping(prefix = "checkout")
public interface CheckoutConfig {

    Sqs sqs();
    Idempotencia idempotencia();
    Vr vr();

    interface Sqs {
        @WithDefault("true") boolean habilitado();
        @WithDefault("http://localhost:4566") String endpoint();
        @WithDefault("us-east-1") String region();
        @WithDefault("fila-notificacao-checkout") String fila();
    }

    interface Idempotencia {
        @WithDefault("10") int ttlSegundos();
    }

    interface Vr {
        @WithDefault("300") double limite();
    }
}
