package com.dprev.checkout.modules.pagamento.repository;

import com.dprev.checkout.core.config.AppConfig;
import com.dprev.checkout.modules.pagamento.domain.MetodoPagamento;
import com.dprev.checkout.modules.pagamento.service.IdempotencyKeyGenerator;
import redis.clients.jedis.Jedis;
import java.util.List;

public class PagamentoIdempotenteRedisRepository implements PagamentoRepository {

    private final PagamentoRepository proximoRepository;
    private final IdempotencyKeyGenerator keyGenerator;
    
    private static final String REDIS_HOST = AppConfig.get("redis.host", "localhost");
    private static final int REDIS_PORT = AppConfig.getInt("redis.port", 6379);
    private static final String REDIS_USER = AppConfig.get("redis.user", "default");
    private static final String REDIS_PASSWORD = AppConfig.get("redis.password", "SuaSenhaSuperSegura123");

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

            // 🚀 ABORDAGEM ATÔMICA CORRETA: Tenta gravar direto com NX e EX (10 segundos)
            // Se a chave já existir, o Redis retorna null. Se não existir, grava e retorna "OK".
            String resultado = jedis.set(
                chaveIdempotencia, 
                "processando", 
                new redis.clients.jedis.params.SetParams().nx().ex(10)
            );

            // Se o resultado for nulo, significa que a chave já estava lá (requisição duplicada)
            if (resultado == null) {
                System.err.println("❌ [REDIS BLOQUEIO] Chave '" + chaveIdempotencia + "' já está em processamento!");
                throw new IllegalStateException("Requisição duplicada em processamento. Tente novamente em breve.");
            }

            System.out.println("🗄️ [REDIS] Chave 🔑 " + chaveIdempotencia + " travada com sucesso por 10s.");

        } catch (IllegalStateException e) {
            throw e; // Repassa bloqueios de idempotência legítimos
        } catch (Exception e) {
            System.err.println("🚨 [REDIS FAIL-OPEN] Erro de infraestrutura no Redis. Prosseguindo direto para o Banco: " + e.getMessage());
            skipRedis = true; // Se o Redis falhar, aplica o Fail-Open
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