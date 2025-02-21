package com.rafa.autorizador.cartao.exception.modelo;

import com.rafa.autorizador.cartao.exception.StatusErroEnum;

import lombok.Getter;

@Getter
public class SaldoInsuficienteException extends Exception {
    private StatusErroEnum statusErro;

    public SaldoInsuficienteException() {
        this.statusErro = StatusErroEnum.SALDO_INSUFICIENTE;
    }
}
