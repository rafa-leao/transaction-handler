package com.rafa.autorizador.cartao.modelo;

import java.math.BigDecimal;
import java.util.Optional;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cartao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String numeroCartao;
    private String senha;
    private BigDecimal saldo;

    public static Cartao converte(CartaoRecord cartaoRecord) {
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao(cartaoRecord.numeroCartao());
        cartao.setSenha(cartaoRecord.senha());
        cartao.setSaldo(BigDecimal.valueOf(500.00));
        return cartao;
    }

    public Optional<Cartao> valida(String senha) {
        return Optional.of(this).filter(c -> c.getSenha().equals(senha));
    }

    public Optional<Cartao> valida(BigDecimal valor) {
        return Optional.of(this).filter(c -> c.getSaldo().compareTo(valor) >= 0);
    }

    public void subtraiSaldo(BigDecimal valor) {
        this.saldo = this.saldo.subtract(valor);
    }
}
