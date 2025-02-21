package com.rafa.autorizador.transacao.exception.modelo;

public class CartaoInexistenteParaTransacaoException extends TransacaoException {
    public CartaoInexistenteParaTransacaoException() {
        super(StatusErroEnum.CARTAO_INEXISTENTE);
    }
}
