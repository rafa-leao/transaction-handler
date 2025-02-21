package com.rafa.autorizador.cartao.modelo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import jakarta.persistence.LockModeType;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Cartao> findByNumeroCartao(String numeroCartao);
}
