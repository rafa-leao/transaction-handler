package com.rafa.autorizador;

import java.math.BigDecimal;

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
import com.rafa.autorizador.config.exception.SaldoInsuficienteException;
import com.rafa.autorizador.config.exception.SenhaInvalidaException;
import com.rafa.autorizador.transacao.AutorizadorTransacao;
import com.rafa.autorizador.transacao.TransacaoRecord;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

@RestController
public class AutorizadorController {
    private @Autowired CriadorCartao criadorCartao;
    private @Autowired BuscadorSaldo buscadorSaldo;
    private @Autowired AutorizadorTransacao autorizadorTransacao;

    @PostMapping("/cartoes")
    public ResponseEntity<CartaoRecord> criaCartao(@Valid @RequestBody CartaoRecord cartao)
            throws CartaoExistenteException {
        return ResponseEntity.status(HttpStatus.CREATED).body(criadorCartao.cria(cartao));
    }

    @GetMapping("/cartoes/{numeroCartao}")
    public ResponseEntity<BigDecimal> buscaSaldo(@PathVariable @Size(min = 16, max = 16) String numeroCartao)
            throws CartaoInexistenteException {
        return ResponseEntity.ok(buscadorSaldo.busca(numeroCartao));
    }

    @PostMapping("/transacoes")
    public ResponseEntity<String> autorizaTransacao(@Valid @RequestBody TransacaoRecord transacaoRecord)
            throws CartaoInexistenteException, SaldoInsuficienteException, SenhaInvalidaException {
        return ResponseEntity.status(HttpStatus.CREATED).body(autorizadorTransacao.autoriza(transacaoRecord));
    }
}
