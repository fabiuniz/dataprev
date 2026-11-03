package com.dprev.checkout.modules.pagamento.domain.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

// Jakarta Persistence (contrato) + Hibernate (implementação), via Panache.
@Entity
@Table(name = "tb_pagamentos")
public class PagamentoEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "metodo_id", nullable = false)
    public String metodoId;

    @Column(nullable = false, precision = 15, scale = 2)
    public BigDecimal valor;

    @Column(name = "email_cliente", nullable = false)
    public String emailCliente;

    @Column(nullable = false)
    public String status;

    // Mecanismo atômico definitivo contra concorrência: UNIQUE no banco.
    @Column(name = "chave_idempotencia", nullable = false, unique = true)
    public String chaveIdempotencia;

    @Column(name = "data_criacao", updatable = false)
    public LocalDateTime dataCriacao = LocalDateTime.now();
}
