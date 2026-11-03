package com.dprev.checkout.modules.pagamento.repository;

import com.dprev.checkout.core.exception.RequisicaoDuplicadaException;
import com.dprev.checkout.modules.pagamento.domain.Pagamento;
import com.dprev.checkout.modules.pagamento.domain.entity.PagamentoEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PagamentoPostgresRepository implements PagamentoRepository, PanacheRepository<PagamentoEntity> {

    @Override
    public Pagamento salvar(Pagamento p) {
        PagamentoEntity e = new PagamentoEntity();
        e.metodoId = p.metodo();
        e.valor = p.valor();
        e.emailCliente = p.email();
        e.status = "PROCESSADO";
        e.chaveIdempotencia = p.chaveIdempotencia();

        try {
            // flush imediato: a violação do UNIQUE estoura AQUI (e não no commit)
            persistAndFlush(e);
        } catch (PersistenceException ex) {
            if (violacaoDeUnicidade(ex)) {
                throw new RequisicaoDuplicadaException(
                        "Chave de idempotência já utilizada: " + p.chaveIdempotencia());
            }
            throw ex;
        }
        Log.infof("🐘 [POSTGRES/Hibernate] Pagamento %s de R$ %s persistido ✅", e.metodoId, e.valor);
        return paraDominio(e);
    }

    @Override
    public Optional<Pagamento> buscarPorId(Long id) {
        return findByIdOptional(id).map(PagamentoPostgresRepository::paraDominio);
    }

    @Override
    public List<Pagamento> listarTodos() {
        return listAll().stream().map(PagamentoPostgresRepository::paraDominio).toList();
    }

    private static boolean violacaoDeUnicidade(Throwable t) {
        while (t != null) {
            if (t instanceof org.hibernate.exception.ConstraintViolationException) {
                return true;
            }
            t = t.getCause();
        }
        return false;
    }

    private static Pagamento paraDominio(PagamentoEntity e) {
        return new Pagamento(e.id, e.metodoId, e.valor, e.emailCliente, e.status, e.chaveIdempotencia);
    }
}
