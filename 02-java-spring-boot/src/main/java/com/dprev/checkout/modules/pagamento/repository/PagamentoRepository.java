package com.dprev.checkout.modules.pagamento.repository;

import com.dprev.checkout.modules.pagamento.domain.MetodoPagamento;
import java.util.List;

public interface PagamentoRepository {
    void salvar(MetodoPagamento pagamento, double valor, String emailCliente, String chaveIdempotencia);
    MetodoPagamento buscarPorId(Long id);
    List<MetodoPagamento> listarTodos();
}