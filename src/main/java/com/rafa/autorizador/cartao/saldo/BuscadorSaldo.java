package com.rafa.autorizador.cartao.saldo;

import com.rafa.autorizador.config.exception.CartaoInexistenteException;

public interface BuscadorSaldo {
    Double busca(String numeroCartao) throws CartaoInexistenteException;
}
