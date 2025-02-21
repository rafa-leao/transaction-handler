package com.rafa.autorizador.cartao;

import com.rafa.autorizador.cartao.exception.modelo.CartaoExistenteException;
import com.rafa.autorizador.cartao.modelo.CartaoRecord;

public interface CriadorCartao {
    CartaoRecord cria(CartaoRecord cartaoRecord) throws CartaoExistenteException;
}
