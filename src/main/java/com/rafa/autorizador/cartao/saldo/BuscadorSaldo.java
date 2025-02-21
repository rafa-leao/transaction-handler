package com.rafa.autorizador.cartao.saldo;

import java.math.BigDecimal;

import com.rafa.autorizador.cartao.exception.modelo.CartaoInexistenteException;

public interface BuscadorSaldo {
    BigDecimal busca(String numeroCartao) throws CartaoInexistenteException;
}
