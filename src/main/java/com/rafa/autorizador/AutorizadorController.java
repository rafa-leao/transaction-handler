package com.rafa.autorizador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rafa.autorizador.cartao.CartaoRecord;
import com.rafa.autorizador.cartao.ManipuladorCartao;
import com.rafa.autorizador.cartao.exception.CartaoExistenteException;

import jakarta.validation.Valid;

@RestController
public class AutorizadorController {
    private @Autowired ManipuladorCartao manipulador;

    @PostMapping("/cartoes")
    public ResponseEntity<CartaoRecord> criaCartao(@Valid @RequestBody CartaoRecord cartao) throws CartaoExistenteException {
        return ResponseEntity.status(HttpStatus.CREATED).body(manipulador.cria(cartao));
    }
}
