package com.dprev.checkout.modules.pagamento.repository;

import com.dprev.checkout.modules.pagamento.domain.MetodoPagamento;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.ArrayList;

public class PagamentoJdbcRepository implements PagamentoRepository {

    private Connection getConnection() throws Exception {
        Class.forName("org.h2.Driver");
        //String url = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1"; // Mantém o banco vivo na memória do processo
        //Connection conn = DriverManager.getConnection(url, "sa", "");

        // Em um repositório SQLite real, a URL seria um arquivo .db:
        String url = "jdbc:sqlite:pagamentos.db";
        Connection conn = DriverManager.getConnection(url);

        // Cria a tabela automaticamente se ela não existir
        String createTableSql = """
        CREATE TABLE IF NOT EXISTS pagamentos (
            id INT AUTO_INCREMENT PRIMARY KEY,
            tipo VARCHAR(50) NOT NULL,
            valor DOUBLE NOT NULL
        )
    """;

        try (PreparedStatement stmt = conn.prepareStatement(createTableSql)) {
            stmt.execute();
        }

        return conn;
    }

    @Override
    public void salvar(MetodoPagamento pagamento, double valor, String emailCliente, String chaveIdempotencia) {
        String sql = "INSERT INTO pagamentos (tipo, valor) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // stmt.setString(1, pagamento.getTipo());
            // stmt.setDouble(2, pagamento.getValor());
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public MetodoPagamento buscarPorId(Long id) {
        // Implementação futura do SELECT
        return null;
    }

    @Override
    public List<MetodoPagamento> listarTodos() {
        // Implementação futura do SELECT *
        return new ArrayList<>();
    }
}