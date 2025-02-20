package com.rafa.autorizador.cartao;

import com.rafa.autorizador.config.exception.CartaoExistenteException;

public interface ManipuladorCartao {
    CartaoRecord cria(CartaoRecord cartaoRecord) throws CartaoExistenteException;
}
