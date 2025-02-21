package com.rafa.autorizador.cartao.exception.modelo;

import com.rafa.autorizador.cartao.exception.StatusErroEnum;

import lombok.Getter;

@Getter
public class SenhaInvalidaException extends Exception {
    private StatusErroEnum statusErro;

    public SenhaInvalidaException() {
        this.statusErro = StatusErroEnum.SENHA_INVALIDA;
    }
}
