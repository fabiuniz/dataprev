package com.dprev.checkout.modules.pagamento.repository;

import com.dprev.checkout.core.exception.RequisicaoDuplicadaException;
import com.dprev.checkout.modules.pagamento.domain.Pagamento;
import com.dprev.checkout.modules.pagamento.domain.entity.PagamentoEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@ApplicationScoped
public class PagamentoPostgresRepository implements PagamentoRepository {

    private static final Logger LOG = Logger.getLogger(PagamentoPostgresRepository.class.getName());

    @PersistenceContext(unitName = "checkout")
    EntityManager em;

    @Override
    @Transactional
    public Pagamento salvar(Pagamento p) {
        PagamentoEntity e = new PagamentoEntity();
        e.metodoId = p.metodo();
        e.valor = p.valor();
        e.emailCliente = p.email();
        e.status = "PROCESSADO";
        e.chaveIdempotencia = p.chaveIdempotencia();

        try {
            em.persist(e);
            em.flush();
        } catch (PersistenceException ex) {
            if (violacaoDeUnicidade(ex)) {
                throw new RequisicaoDuplicadaException("Chave de idempotência já utilizada: " + p.chaveIdempotencia());
            }
            throw ex;
        }
        LOG.info("🐘 [POSTGRES/JPA] Pagamento persistido ✅");
        return paraDominio(e);
    }

    @Override
    public Optional<Pagamento> buscarPorId(Long id) {
        PagamentoEntity e = em.find(PagamentoEntity.class, id);
        return Optional.ofNullable(e).map(PagamentoPostgresRepository::paraDominio);
    }

    @Override
    public List<Pagamento> listarTodos() {
        return em.createQuery("SELECT e FROM PagamentoEntity e", PagamentoEntity.class)
                .getResultList().stream().map(PagamentoPostgresRepository::paraDominio).toList();
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