package com.dprev.checkout.modules.pagamento.repository;

import com.dprev.checkout.core.config.CheckoutConfig;
import com.dprev.checkout.core.exception.RequisicaoDuplicadaException;
import com.dprev.checkout.modules.pagamento.domain.Pagamento;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.inject.Inject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@ApplicationScoped
@Alternative
@Priority(1)
public class PagamentoIdempotenteRedisRepository implements PagamentoRepository {

    private static final Logger LOG = Logger.getLogger(PagamentoIdempotenteRedisRepository.class.getName());
    private static final String PREFIXO = "trava:checkout:";

    @Inject PagamentoPostgresRepository proximo;
    @Inject CheckoutConfig config;

    private Jedis jedis;

    @PostConstruct
    void init() {
        this.jedis = new Jedis("localhost", 6379);
        this.jedis.auth("SuaSenhaSuperSegura123");
    }

    @Override
    public Pagamento salvar(Pagamento pagamento) {
        String chave = PREFIXO + pagamento.chaveIdempotencia();
        boolean travado = false;

        try {
            SetParams params = new SetParams().nx().ex(config.idempotencia().ttlSegundos());
            String res = jedis.set(chave, "processando", params);
            if (res == null) {
                LOG.warning("❌ [REDIS BLOQUEIO] Chave duplicada!");
                throw new RequisicaoDuplicadaException("Requisição duplicada em processamento.");
            }
            travado = true;
        } catch (RequisicaoDuplicadaException e) {
            throw e;
        } catch (Exception e) {
            LOG.severe("🚨 [REDIS FAIL-OPEN] Indisponível, seguindo para o banco: " + e.getMessage());
        }

        try {
            return proximo.salvar(pagamento);
        } catch (RuntimeException e) {
            if (travado) {
                liberar(chave);
            }
            throw e;
        }
    }

    private void liberar(String chave) {
        try {
            jedis.del(chave);
        } catch (Exception ignored) {}
    }

    @Override public Optional<Pagamento> buscarPorId(Long id) { return proximo.buscarPorId(id); }
    @Override public List<Pagamento> listarTodos() { return proximo.listarTodos(); }
}