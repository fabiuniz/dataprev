package com.dprev.checkout.modules.pagamento.domain.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_pagamentos")
public class PagamentoEntity {

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

    @Column(name = "chave_idempotencia", nullable = false, unique = true)
    public String chaveIdempotencia;

    @Column(name = "data_criacao", updatable = false)
    public LocalDateTime dataCriacao = LocalDateTime.now();
}