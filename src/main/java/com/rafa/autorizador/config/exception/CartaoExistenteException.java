package com.rafa.autorizador.config.exception;

import com.rafa.autorizador.cartao.CartaoRecord;

import lombok.Getter;

@Getter
public class CartaoExistenteException extends Exception {
    private CartaoRecord cartaoRecord;

    public CartaoExistenteException(CartaoRecord cartaoRecord) {
        this.cartaoRecord = cartaoRecord;
    }
    
}
