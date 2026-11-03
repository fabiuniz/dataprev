package com.dprev.checkout.modules.pagamento.repository;

import com.dprev.checkout.modules.pagamento.domain.Pagamento;
import java.util.List;
import java.util.Optional;

public interface PagamentoRepository {
    Pagamento salvar(Pagamento pagamento);
    Optional<Pagamento> buscarPorId(Long id);
    List<Pagamento> listarTodos();
}
