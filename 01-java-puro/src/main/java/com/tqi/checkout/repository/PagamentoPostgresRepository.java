package com.tqi.checkout.repository;

import com.tqi.checkout.domain.MetodoPagamento;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PagamentoPostgresRepository implements PagamentoRepository {

    private static final String HOST = "localhost";
    private static final String PORT = "5432";
    private static final String DB_NAME = "checkout_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    private Connection getConnection() throws Exception {
        // Carrega o driver do Postgres em runtime (Java Puro)
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DB_NAME;
        return DriverManager.getConnection(url, USER, PASSWORD);
    }

    @Override
    public void salvar(MetodoPagamento pagamento, double valor, String emailCliente, String chaveIdempotencia) {
        String nomePagamento = pagamento.getClass().getSimpleName();
        
        String createTableSql = """
            CREATE TABLE IF NOT EXISTS tb_pagamentos (
                id SERIAL PRIMARY KEY,
                metodo_id VARCHAR(100) NOT NULL,
                valor NUMERIC(10, 2) NOT NULL,
                email_cliente VARCHAR(255) NOT NULL,
                status VARCHAR(50) NOT NULL,
                chave_idempotencia VARCHAR(255) NOT NULL UNIQUE
            );
        """;
        
        String insertSql = """
            INSERT INTO tb_pagamentos (metodo_id, valor, email_cliente, status, chave_idempotencia) 
            VALUES (?, ?, ?, ?, ?);
        """;

        // Abre a conexão e executa o bloco de transação de forma segura
        try (Connection conn = getConnection()) {
            // 1. Garante que a tabela existe
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(createTableSql);
            }

            // 2. Insere o registro real no Postgres
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setString(1, nomePagamento);
                pstmt.setDouble(2, valor);
                pstmt.setString(3, emailCliente);
                pstmt.setString(4, "PROCESSADO");
                pstmt.setString(5, chaveIdempotencia); // Resolvido: Vinculando a chave real contra colisões UNIQUE
                
                pstmt.executeUpdate();
            }
            System.out.println("🐘 [POSTGRES REAL] Transação de R$ " + valor + " gravada com sucesso! ✅");

        } catch (Exception e) {
            System.err.println("🚨 🐘 [POSTGRES ERRO] Falha crítica ao salvar no banco: " + e.getMessage());
            throw new RuntimeException("Erro ao persistir transação no banco de dados.", e); 
        }
    }

    @Override
    public MetodoPagamento buscarPorId(Long id) { return null; }
    @Override
    public List<MetodoPagamento> listarTodos() { return new ArrayList<>(); }
}