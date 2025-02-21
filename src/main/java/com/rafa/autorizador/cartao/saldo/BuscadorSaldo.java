package com.rafa.autorizador.cartao.saldo;

import java.math.BigDecimal;

import com.rafa.autorizador.config.exception.CartaoInexistenteException;

public interface BuscadorSaldo {
    BigDecimal busca(String numeroCartao) throws CartaoInexistenteException;
}
