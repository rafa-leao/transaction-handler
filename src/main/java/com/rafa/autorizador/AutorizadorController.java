package com.rafa.autorizador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rafa.autorizador.cartao.CartaoRecord;
import com.rafa.autorizador.cartao.CriadorCartao;
import com.rafa.autorizador.cartao.saldo.BuscadorSaldo;
import com.rafa.autorizador.config.exception.CartaoExistenteException;
import com.rafa.autorizador.config.exception.CartaoInexistenteException;

import jakarta.validation.Valid;

@RestController
public class AutorizadorController {
    private @Autowired CriadorCartao criadorCartao;
    private @Autowired BuscadorSaldo buscadorSaldo;

    @PostMapping("/cartoes")
    public ResponseEntity<CartaoRecord> criaCartao(@Valid @RequestBody CartaoRecord cartao) throws CartaoExistenteException {
        return ResponseEntity.status(HttpStatus.CREATED).body(criadorCartao.cria(cartao));
    }

    @GetMapping("/cartoes/{numeroCartao}")
    public ResponseEntity<Double> buscaSaldo(@PathVariable String numeroCartao) throws CartaoInexistenteException {
        return ResponseEntity.ok(buscadorSaldo.busca(numeroCartao));
    }
}
