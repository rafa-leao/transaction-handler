package com.rafa.autorizador.config.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.LockTimeoutException;
import jakarta.persistence.PessimisticLockException;

@RestControllerAdvice
public class LockControllerAdvice {
    @ExceptionHandler(PessimisticLockException.class)
    public ResponseEntity<String> handlePessimisticLockException(PessimisticLockException e) {
        return unprocessableEntity();
    }

    @ExceptionHandler(LockTimeoutException.class)
    public ResponseEntity<String> handleLockTimeoutException(LockTimeoutException e) {
        return unprocessableEntity();
    }

    private ResponseEntity<String> unprocessableEntity() {
        return ResponseEntity.unprocessableEntity().body("Tente novamente");
    }
}
