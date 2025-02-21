package com.rafa.autorizador.transacao;

import com.rafa.autorizador.config.exception.CartaoInexistenteException;
import com.rafa.autorizador.config.exception.SaldoInsuficienteException;
import com.rafa.autorizador.config.exception.SenhaInvalidaException;

public interface AutorizadorTransacao {
    String autoriza(TransacaoRecord transacaoRecord)
            throws CartaoInexistenteException, SaldoInsuficienteException, SenhaInvalidaException;
}
