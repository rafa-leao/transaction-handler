package com.rafa.autorizador.cartao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rafa.autorizador.cartao.CartaoRecord;

@RestControllerAdvice
public class CartaoControllerAdvice {
    @ExceptionHandler(CartaoExistenteException.class)
    public ResponseEntity<CartaoRecord> handleCartaoExistenteException(CartaoExistenteException e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getCartaoRecord());
    }
}
