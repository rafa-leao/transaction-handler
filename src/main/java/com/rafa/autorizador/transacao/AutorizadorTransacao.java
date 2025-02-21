package com.rafa.autorizador.transacao;

import com.rafa.autorizador.transacao.exception.modelo.TransacaoException;

public interface AutorizadorTransacao {
    String autoriza(TransacaoRecord transacaoRecord) throws TransacaoException;
}
