package com.rafa.autorizador.config.exception;

import lombok.Getter;

@Getter
public class CartaoInexistenteException extends Exception {
    private StatusErroEnum statusErroEnum;

    public CartaoInexistenteException() {
        this.statusErroEnum = StatusErroEnum.CARTAO_INEXISTENTE;
    }
}
