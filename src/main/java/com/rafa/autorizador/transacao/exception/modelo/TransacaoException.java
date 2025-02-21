package com.rafa.autorizador.transacao.exception.modelo;

import lombok.Getter;

@Getter
public class TransacaoException extends Exception {
    private StatusErroEnum statusErro;

    public TransacaoException(StatusErroEnum se) {
        this.statusErro = se;
    }
}
