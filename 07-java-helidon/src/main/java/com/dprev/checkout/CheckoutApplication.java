package com.dprev.checkout;

import com.dprev.checkout.core.exception.GlobalExceptionHandler;
import com.dprev.checkout.modules.pagamento.controller.CheckoutResource;
import com.dprev.checkout.modules.pagamento.controller.IndexResource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.Set;

@ApplicationScoped
@ApplicationPath("/")
public class CheckoutApplication extends Application {
    @Override
        public Set<Class<?>> getClasses() {
            return Set.of(CheckoutResource.class, IndexResource.class, GlobalExceptionHandler.class);
        }
}