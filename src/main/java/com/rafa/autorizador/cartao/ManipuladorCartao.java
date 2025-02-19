package com.rafa.autorizador.cartao;

import com.rafa.autorizador.cartao.exception.CartaoExistenteException;

public interface ManipuladorCartao {
    CartaoRecord cria(CartaoRecord cartaoRecord) throws CartaoExistenteException;
}
