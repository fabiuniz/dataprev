package com.dprev.checkout.modules.pagamento.repository;

import com.dprev.checkout.modules.pagamento.domain.MetodoPagamento;
import com.dprev.checkout.modules.pagamento.domain.entity.PagamentoEntity;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PagamentoPostgresRepository implements PagamentoRepository {

    private final SpringDataPagamentoRepository springDataRepository;

    public PagamentoPostgresRepository(SpringDataPagamentoRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public void salvar(MetodoPagamento pagamento, double valorPrimitivo, String emailCliente, String chaveIdempotencia) {
        String nomePagamento = pagamento.getClass().getSimpleName();
        BigDecimal valor = BigDecimal.valueOf(valorPrimitivo);

        PagamentoEntity entity = new PagamentoEntity(nomePagamento, valor, emailCliente, "PROCESSADO", chaveIdempotencia);
        
        springDataRepository.save(entity);
        
        System.out.println("🐘 [02-SPRING-BOOT] Hibernate persistiu o pagamento no PostgreSQL com sucesso! ✅");
    }

    @Override
    public MetodoPagamento buscarPorId(Long id) { return null; }

    @Override
    public List<MetodoPagamento> listarTodos() { return new ArrayList<>(); }
}