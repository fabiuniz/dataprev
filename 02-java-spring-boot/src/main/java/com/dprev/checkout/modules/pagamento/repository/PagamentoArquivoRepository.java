package com.dprev.checkout.modules.pagamento.repository;

import com.dprev.checkout.modules.pagamento.domain.MetodoPagamento;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PagamentoArquivoRepository implements PagamentoRepository {
    private static final String FILE_NAME = "pagamentos.txt";

    @Override
    public void salvar(MetodoPagamento pagamento, double valor, String emailCliente, String chaveIdempotencia) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            // 🚀 PEGA SÓ O NOME DA CLASSE (Ex: "PixPagamento", "CartaoCreditoPagamento")
            String nomePagamento = pagamento.getClass().getSimpleName();
            // Grava de forma muito mais limpa no TXT
            writer.write("🏛️[SUCESSO] Pagamento registrado via: " + nomePagamento);
            writer.newLine();
            System.out.println("💾[ARQUIVO] Dados salvos com sucesso no arquivo txt!");
        } catch (IOException e) {
            System.err.println("❌💾[ERRO ARQUIVO] Falha ao escrever no arquivo: " + e.getMessage());
        }
    }

    @Override
    public MetodoPagamento buscarPorId(Long id) { /* Implementar lógica de leitura */ return null; }

    @Override
    public List<MetodoPagamento> listarTodos() { /* Implementar lógica de leitura */ return new ArrayList<>(); }
}