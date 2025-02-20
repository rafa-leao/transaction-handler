package com.rafa.autorizador.cartao;

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
    private Double saldo;

    public static Cartao converte(CartaoRecord cartaoRecord) {
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao(cartaoRecord.numeroCartao());
        cartao.setSenha(cartaoRecord.senha());
        cartao.setSaldo(500.0);
        return cartao;
    }
}
