package com.dprev.checkout;

import com.dprev.checkout.modules.pagamento.notification.Notificador;
import io.quarkus.test.Mock;
import jakarta.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// @Mock = alternativa ativa só nos testes: substitui o SqsNotificadorService.
@Mock
@ApplicationScoped
public class NotificadorFake implements Notificador {

    public final List<String> enviados = new CopyOnWriteArrayList<>();

    @Override
    public void enviarComprovante(String destino, BigDecimal valor) {
        enviados.add(destino + ":" + valor);
    }
}
