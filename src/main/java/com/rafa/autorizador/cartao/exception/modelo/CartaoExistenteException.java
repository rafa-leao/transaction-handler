package com.rafa.autorizador.cartao.exception.modelo;

import com.rafa.autorizador.cartao.modelo.CartaoRecord;

import lombok.Getter;

@Getter
public class CartaoExistenteException extends Exception {
    private CartaoRecord cartaoRecord;

    public CartaoExistenteException(CartaoRecord cartaoRecord) {
        this.cartaoRecord = cartaoRecord;
    }
    
}
