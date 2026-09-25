package com.dprev.checkout.repository;

import com.dprev.checkout.domain.entity.PagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataPagamentoRepository extends JpaRepository<PagamentoEntity, Long> {
}