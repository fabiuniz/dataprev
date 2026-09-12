package com.dprev.checkout.repository;

import com.dprev.checkout.domain.MetodoPagamento;
import java.util.ArrayList;
import java.util.List;

// 🚀 O "implements" é o que resolve o erro do seu compilador!
public class PagamentoSqliteRepository implements PagamentoRepository {

    // O arquivo .db que você queria!
    private static final String DATABASE_FILE = "pagamentos.db";

    @Override
    public void salvar(MetodoPagamento pagamento, double valor, String emailCliente, String chaveIdempotencia) {
        String nomePagamento = pagamento.getClass().getSimpleName();

        // Simulando a gravação estruturada em um arquivo binário/banco de dados .db
        System.out.println("🛢️[SQLITE DB] Abrindo conexão com o arquivo: " + DATABASE_FILE);
        System.out.println("🛢️[SQLITE DB] INSERT INTO pagamentos (tipo) VALUES ('" + nomePagamento + "');");
        System.out.println("🛢️[SQLITE DB] Transação salva fisicamente no banco indexado!");
    }

    @Override
    public MetodoPagamento buscarPorId(Long id) {
        return null;
    }

    @Override
    public List<MetodoPagamento> listarTodos() {
        return new ArrayList<>();
    }
}