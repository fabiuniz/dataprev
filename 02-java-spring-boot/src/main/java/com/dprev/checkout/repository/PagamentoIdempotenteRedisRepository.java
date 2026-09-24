package com.dprev.checkout.repository;

import com.dprev.checkout.domain.MetodoPagamento;
import org.springframework.beans.factory.annotation.Value;
import com.dprev.checkout.service.IdempotencyKeyGenerator;
import redis.clients.jedis.Jedis;
import java.util.List;

public class PagamentoIdempotenteRedisRepository implements PagamentoRepository {

    private final PagamentoRepository proximoRepository;
    private final IdempotencyKeyGenerator keyGenerator;
    
    @Value("${redis.host}") private static final String REDIS_HOST = "localhost";
    @Value("${redis.port}") private static final int REDIS_PORT = 6379;
    private static final String REDIS_USER = "default";
    private static final String REDIS_PASSWORD = "SuaSenhaSuperSegura123";

    public PagamentoIdempotenteRedisRepository(PagamentoRepository proximoRepository, IdempotencyKeyGenerator keyGenerator) {
        this.proximoRepository = proximoRepository;
        this.keyGenerator = keyGenerator;
    }

    @Override
    public void salvar(MetodoPagamento pagamento, double valor, String emailCliente, String chaveIdempotencia) {
        String nomePagamento = pagamento.getClass().getSimpleName();
        //String chaveIdempotencia = "trava:checkout:" + nomePagamento.toLowerCase();
        // Se a chave não veio gerada, geramos aqui para manter a consistência
        if (chaveIdempotencia == null || chaveIdempotencia.isEmpty()) {
            String hash = keyGenerator.gerarChaveDeterministica(emailCliente, valor);
            chaveIdempotencia = "trava:checkout:" + nomePagamento.toLowerCase() + ":" + hash;
        }

        boolean skipRedis = false;

        try (Jedis jedis = new Jedis(REDIS_HOST, REDIS_PORT)) {
            // Autentica no Redis
            jedis.auth(REDIS_USER, REDIS_PASSWORD); 
            
            System.out.println("🗄️[REDIS REAL] Conectado e autenticado com sucesso ✅!");
            // 🔍 Verificação de idempotência real no banco em memória
            if (jedis.exists(chaveIdempotencia)) {
                System.err.println("❌ [REDIS BLOQUEIO] Chave '" + chaveIdempotencia + "' ativa!");
                throw new IllegalStateException("Requisição duplicada em processamento. Tente novamente em breve.");
            }

            // ⏱️ Se não existir, salva a chave com TTL (Time-To-Live) de 10 segundos
            // NX = Só define se não existir / EX = Tempo em segundos
            jedis.set(chaveIdempotencia, "processando", new redis.clients.jedis.params.SetParams().nx().ex(10));
            System.out.println("🗄️ [REDIS] Chave 🔑 " + chaveIdempotencia + " travada por 10s.");

        } catch (IllegalStateException e) {
            throw e; // Repassa bloqueios de idempotência legítimos
        } catch (Exception e) {
            System.err.println("🚨 [REDIS FAIL-OPEN] Erro de infraestrutura no Redis. Prosseguindo direto para o Banco: " + e.getMessage());
            skipRedis = true; // Se o Redis morrer, o sistema não para (Fail-Open)
        }

        try {
            // Repassa a chave real e estável para a gravação física no banco
            this.proximoRepository.salvar(pagamento, valor, emailCliente, chaveIdempotencia);
        } catch (Exception e) {
            // SE O BANCO FALHAR, LIMPAMOS A TRAVA DO REDIS IMEDIATAMENTE (Evita falsos travamentos)
            if (!skipRedis) {
                try (Jedis jedis = new Jedis(REDIS_HOST, REDIS_PORT)) {
                    jedis.auth(REDIS_USER, REDIS_PASSWORD);
                    jedis.del(chaveIdempotencia);
                    System.out.println("🧹 [REDIS CLEANUP] Trava removida pós-falha do banco.");
                } catch (Exception re) {
                    System.err.println("🚨 Erro ao limpar trava do Redis: " + re.getMessage());
                }
            }
            throw e; // Borbulha o erro para a controller emitir HTTP 400/500
        }
    }

    @Override
    public MetodoPagamento buscarPorId(Long id) { return proximoRepository.buscarPorId(id); }
    @Override
    public List<MetodoPagamento> listarTodos() { return proximoRepository.listarTodos(); }
}