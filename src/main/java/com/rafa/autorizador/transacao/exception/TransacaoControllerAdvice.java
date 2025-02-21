package com.rafa.autorizador.transacao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rafa.autorizador.transacao.exception.modelo.CartaoInexistenteParaTransacaoException;
import com.rafa.autorizador.transacao.exception.modelo.SaldoInsuficienteException;
import com.rafa.autorizador.transacao.exception.modelo.SenhaInvalidaException;
import com.rafa.autorizador.transacao.exception.modelo.StatusErroEnum;
import com.rafa.autorizador.transacao.exception.modelo.TransacaoException;

@RestControllerAdvice
public class TransacaoControllerAdvice {
    // Retorno para saldo insuficiente
    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<StatusErroEnum> handleSaldoInsuficienteException(SaldoInsuficienteException e) {
        return unprocessableEntity(e);
    }

    // Retorno para senha inválida
    @ExceptionHandler(SenhaInvalidaException.class)
    public ResponseEntity<StatusErroEnum> handleSenhaInvalidaException(SenhaInvalidaException e) {
        return unprocessableEntity(e);
    }

    // Retorno para cartão inexistente durante uma transação
    @ExceptionHandler(CartaoInexistenteParaTransacaoException.class)
    public ResponseEntity<StatusErroEnum> handleCartaoInexistenteParaTransacaoException(
            CartaoInexistenteParaTransacaoException e) {
        return unprocessableEntity(e);
    }

    private ResponseEntity<StatusErroEnum> unprocessableEntity(TransacaoException e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getStatusErro());
    }
}
