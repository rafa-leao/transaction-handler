package com.rafa.autorizador.cartao.exception.modelo;

import com.rafa.autorizador.cartao.exception.StatusErroEnum;

import lombok.Getter;

@Getter
public class CartaoInexistenteException extends Exception {
    private StatusErroEnum statusErroEnum;

    public CartaoInexistenteException() {
        this.statusErroEnum = StatusErroEnum.CARTAO_INEXISTENTE;
    }
}
