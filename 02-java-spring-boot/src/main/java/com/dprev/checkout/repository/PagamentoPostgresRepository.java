package com.dprev.checkout.repository;

import com.dprev.checkout.domain.MetodoPagamento;
import org.springframework.beans.factory.annotation.Value;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PagamentoPostgresRepository implements PagamentoRepository {

    @Value("${spring.datasource.db.host}")
    private String HOST;
    @Value("${spring.datasource.db.port}")
    private String PORT;
    @Value("${spring.datasource.db.name}")
    private String DB_NAME;
    @Value("${spring.datasource.username}")
    private String USER;
    @Value("${spring.datasource.password}")
    private String PASSWORD;

    private Connection getConnection() throws Exception {
        // Carrega o driver do Postgres em runtime (Java Puro)
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DB_NAME;
        Connection conn = DriverManager.getConnection(url, USER, PASSWORD);

        // 🛠️ Integrado na rotina: Cria a tabela automaticamente se ela não existir
        String createTableSql = """
            CREATE TABLE IF NOT EXISTS tb_pagamentos (
                id SERIAL PRIMARY KEY,
                metodo_id VARCHAR(50) NOT NULL,
                valor NUMERIC(15, 2) NOT NULL,
                email_cliente VARCHAR(255) NOT NULL,
                status VARCHAR(50) NOT NULL,
                chave_idempotencia VARCHAR(255) NOT NULL,
                data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
        """;

        try (PreparedStatement stmt = conn.prepareStatement(createTableSql)) {
            stmt.execute();
        }

        return conn;
    }

    @Override
    public void salvar(MetodoPagamento pagamento, double valorPrimitivo, String emailCliente, String chaveIdempotencia) {
        String nomePagamento = pagamento.getClass().getSimpleName();
        BigDecimal valor = BigDecimal.valueOf(valorPrimitivo); // Conversão para evitar imprecisão de double
        
        String insertSql = """
            INSERT INTO tb_pagamentos (metodo_id, valor, email_cliente, status, chave_idempotencia) 
            VALUES (?, ?, ?, ?, ?);
        """;

        // Abre a conexão e executa o bloco de transação de forma segura
        try (Connection conn = getConnection()) {
            // 1. Controle Transacional Explícito (Exigência FGV)
            conn.setAutoCommit(false); 

            // 2. Insere o registro real no Postgres
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setString(1, nomePagamento);
                pstmt.setBigDecimal(2, valor); // Uso de setBigDecimal para colunas NUMERIC
                pstmt.setString(3, emailCliente);
                pstmt.setString(4, "PROCESSADO");
                pstmt.setString(5, chaveIdempotencia);
                
                pstmt.executeUpdate();
                
                // 2. Confirma a transação se tudo ocorrer bem
                conn.commit();
                System.out.println("🐘 [POSTGRES REAL] Transação de R$ " + valor + " gravada com sucesso! ✅");
            } catch (SQLException e) {
                // 3. Reverte a transação em caso de erro no SQL
                conn.rollback();
                System.err.println("🚨 🐘 [POSTGRES ROLLBACK] Transação revertida devido a erro: " + e.getMessage());
                throw e;
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