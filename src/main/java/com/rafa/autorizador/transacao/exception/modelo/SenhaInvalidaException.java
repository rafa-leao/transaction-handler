package com.rafa.autorizador.transacao.exception.modelo;

public class SenhaInvalidaException extends TransacaoException {
    public SenhaInvalidaException() {
        super(StatusErroEnum.SENHA_INVALIDA);
    }
}
