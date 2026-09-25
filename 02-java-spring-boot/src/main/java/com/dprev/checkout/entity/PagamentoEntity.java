package com.dprev.checkout.domain.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_pagamentos")
public class PagamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "metodo_id", nullable = false)
    private String metodoId;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;

    @Column(name = "email_cliente", nullable = false)
    private String emailCliente;

    @Column(nullable = false)
    private String status;

    @Column(name = "chave_idempotencia", unique = true)
    private String chaveIdempotencia;

    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    public PagamentoEntity() {}

    public PagamentoEntity(String metodoId, BigDecimal valor, String emailCliente, String status, String chaveIdempotencia) {
        this.metodoId = metodoId;
        this.valor = valor;
        this.emailCliente = emailCliente;
        this.status = status;
        this.chaveIdempotencia = chaveIdempotencia;
    }
}