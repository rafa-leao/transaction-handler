package com.rafa.autorizador.cartao;

import com.rafa.autorizador.config.exception.CartaoExistenteException;

public interface CriadorCartao {
    CartaoRecord cria(CartaoRecord cartaoRecord) throws CartaoExistenteException;
}
