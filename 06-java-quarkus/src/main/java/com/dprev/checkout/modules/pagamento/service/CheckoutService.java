package com.dprev.checkout.modules.pagamento.service;

import com.dprev.checkout.core.exception.MetodoPagamentoInvalidoException;
import com.dprev.checkout.modules.pagamento.domain.MetodoPagamento;
import com.dprev.checkout.modules.pagamento.domain.Pagamento;
import com.dprev.checkout.modules.pagamento.notification.Notificador;
import com.dprev.checkout.modules.pagamento.repository.PagamentoRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;

/**
 * [S] Orquestra o checkout.
 * [O] Não conhece nenhuma Strategy concreta: o CDI injeta TODAS as implementações
 *     de MetodoPagamento via Instance<> (some o switch do controller).
 * [D] Depende só de abstrações (MetodoPagamento, PagamentoRepository, Notificador).
 */
@ApplicationScoped
public class CheckoutService {

    private final Instance<MetodoPagamento> metodos;
    private final PagamentoRepository repository;
    private final Notificador notificador;
    private final IdempotencyKeyGenerator keyGenerator;

    public CheckoutService(Instance<MetodoPagamento> metodos,
                           PagamentoRepository repository,
                           Notificador notificador,
                           IdempotencyKeyGenerator keyGenerator) {
        this.metodos = metodos;
        this.repository = repository;
        this.notificador = notificador;
        this.keyGenerator = keyGenerator;
    }

    @Transactional
    public Pagamento finalizarPedido(String codigoMetodo, BigDecimal valor, String email, String chaveIdempotencia) {
        Log.info("--- Iniciando Checkout ---");

        MetodoPagamento metodo = resolver(codigoMetodo);

        // 1) Regras do meio de pagamento ANTES de travar qualquer recurso
        //    (se o VR estourar o limite, nada fica preso no Redis).
        metodo.processar(valor);

        // 2) Persistência (decorator Redis -> Postgres)
        String chave = (chaveIdempotencia == null || chaveIdempotencia.isBlank())
                ? keyGenerator.gerarChaveAleatoria()
                : chaveIdempotencia.trim();

        Pagamento salvo = repository.salvar(
                new Pagamento(null, metodo.codigo(), valor, email, "PROCESSADO", chave));

        // 3) Notificação tolerante a falhas (fallback interno)
        notificador.enviarComprovante(email, valor);

        Log.info("--- Checkout Finalizado ---");
        return salvo;
    }

    private MetodoPagamento resolver(String codigo) {
        String normalizado = codigo == null ? "" : codigo.trim();
        return metodos.stream()
                .filter(m -> m.codigo().equalsIgnoreCase(normalizado))
                .findFirst()
                .orElseThrow(() -> new MetodoPagamentoInvalidoException(codigo));
    }
}
