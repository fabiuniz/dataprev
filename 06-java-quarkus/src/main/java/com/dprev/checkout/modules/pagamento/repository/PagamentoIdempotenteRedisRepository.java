package com.dprev.checkout.modules.pagamento.repository;

import com.dprev.checkout.core.config.CheckoutConfig;
import com.dprev.checkout.core.exception.RequisicaoDuplicadaException;
import com.dprev.checkout.modules.pagamento.domain.Pagamento;
import io.quarkus.logging.Log;
import io.quarkus.redis.datasource.RedisDataSource;
import io.vertx.mutiny.redis.client.Response;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * DECORATOR de idempotência (mesma ideia do 01/02).
 *
 * @Alternative + @Priority fazem este bean "ganhar" nos pontos de injeção de
 * PagamentoRepository. O repositório real é injetado pelo tipo concreto.
 *
 * 1) SET chave NX EX ttl  -> trava atômica (null = já existe = duplicado)
 * 2) Redis fora do ar     -> FAIL-OPEN (segue para o banco; o UNIQUE protege)
 * 3) Banco falhou         -> libera a trava (cliente pode tentar de novo)
 */
@ApplicationScoped
@Alternative
@Priority(1)
public class PagamentoIdempotenteRedisRepository implements PagamentoRepository {

    private static final String PREFIXO = "trava:checkout:";

    @Inject PagamentoPostgresRepository proximo;
    @Inject RedisDataSource redis;
    @Inject CheckoutConfig config;

    @Override
    public Pagamento salvar(Pagamento pagamento) {
        String chave = PREFIXO + pagamento.chaveIdempotencia();
        boolean travado = false;

        try {
            Response r = redis.execute("SET", chave, "processando",
                    "NX", "EX", String.valueOf(config.idempotencia().ttlSegundos()));
            if (r == null) {
                Log.warnf("❌ [REDIS BLOQUEIO] Chave '%s' já está em processamento!", chave);
                throw new RequisicaoDuplicadaException(
                        "Requisição duplicada em processamento. Tente novamente em breve.");
            }
            travado = true;
            Log.infof("🗄️ [REDIS] Chave 🔑 %s travada por %ds.", chave, config.idempotencia().ttlSegundos());
        } catch (RequisicaoDuplicadaException e) {
            throw e; // bloqueio legítimo de idempotência
        } catch (Exception e) {
            Log.errorf("🚨 [REDIS FAIL-OPEN] Infra do Redis indisponível, seguindo para o Banco: %s", e.getMessage());
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
            redis.key().del(chave);
            Log.info("🧹 [REDIS CLEANUP] Trava removida pós-falha do banco.");
        } catch (Exception re) {
            Log.errorf("🚨 Erro ao limpar trava do Redis: %s", re.getMessage());
        }
    }

    @Override
    public Optional<Pagamento> buscarPorId(Long id) { return proximo.buscarPorId(id); }

    @Override
    public List<Pagamento> listarTodos() { return proximo.listarTodos(); }
}
