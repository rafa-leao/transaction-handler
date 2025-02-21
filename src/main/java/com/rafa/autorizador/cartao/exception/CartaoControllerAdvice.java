package com.rafa.autorizador.cartao.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rafa.autorizador.cartao.exception.modelo.CartaoExistenteException;
import com.rafa.autorizador.cartao.exception.modelo.CartaoInexistenteException;
import com.rafa.autorizador.cartao.exception.modelo.CartaoInexistenteParaTransacaoException;
import com.rafa.autorizador.cartao.exception.modelo.SaldoInsuficienteException;
import com.rafa.autorizador.cartao.exception.modelo.SenhaInvalidaException;
import com.rafa.autorizador.cartao.modelo.CartaoRecord;

@RestControllerAdvice
public class CartaoControllerAdvice {
    // Retorno para cartão existente
    @ExceptionHandler(CartaoExistenteException.class)
    public ResponseEntity<CartaoRecord> handleCartaoExistenteException(CartaoExistenteException e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getCartaoRecord());
    }

    // Retorno para saldo insuficiente
    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<StatusErroEnum> handleSaldoInsuficienteException(SaldoInsuficienteException e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getStatusErro());
    }

    // Retorno para senha inválida
    @ExceptionHandler(SenhaInvalidaException.class)
    public ResponseEntity<StatusErroEnum> handleSenhaInvalidaException(SenhaInvalidaException e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getStatusErro());
    }

    // Retorno para cartão inexistente durante uma transação
    @ExceptionHandler(CartaoInexistenteParaTransacaoException.class)
    public ResponseEntity<StatusErroEnum> handleCartaoInexistenteParaTransacaoException(CartaoInexistenteParaTransacaoException e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getStatusErroEnum());
    }

    // Retorno para cartão inexistente
    @ExceptionHandler(CartaoInexistenteException.class)
    public ResponseEntity<String> handleCartaoInexistenteException(CartaoInexistenteException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Retorno para validação de campos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> erros = e.getBindingResult().getAllErrors()
                .stream().map(getDefaultMessage -> getDefaultMessage.getDefaultMessage()).toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }

    // Retorno para exceções genéricas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
