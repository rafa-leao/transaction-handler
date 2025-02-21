package com.rafa.autorizador.transacao.exception.modelo;

public class SaldoInsuficienteException extends TransacaoException {
    public SaldoInsuficienteException() {
        super(StatusErroEnum.SALDO_INSUFICIENTE);
    }
}
