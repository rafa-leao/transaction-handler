package com.rafa.autorizador.transacao;

import com.rafa.autorizador.cartao.exception.modelo.CartaoInexistenteException;
import com.rafa.autorizador.cartao.exception.modelo.SaldoInsuficienteException;
import com.rafa.autorizador.cartao.exception.modelo.SenhaInvalidaException;

public interface AutorizadorTransacao {
    String autoriza(TransacaoRecord transacaoRecord)
            throws CartaoInexistenteException, SaldoInsuficienteException, SenhaInvalidaException;
}
