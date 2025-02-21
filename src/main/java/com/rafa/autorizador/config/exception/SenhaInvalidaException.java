package com.rafa.autorizador.config.exception;

import lombok.Getter;

@Getter
public class SenhaInvalidaException extends Exception {
    private StatusErroEnum statusErro;

    public SenhaInvalidaException() {
        this.statusErro = StatusErroEnum.SENHA_INVALIDA;
    }
}
