package com.rafa.autorizador.config.exception;

import lombok.Getter;

@Getter
public class SaldoInsuficienteException extends Exception {
    private StatusErroEnum statusErro;

    public SaldoInsuficienteException() {
        this.statusErro = StatusErroEnum.SALDO_INSUFICIENTE;
    }
}
