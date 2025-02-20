package com.rafa.autorizador.cartao.saldo;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafa.autorizador.cartao.Cartao;
import com.rafa.autorizador.cartao.CartaoRepository;
import com.rafa.autorizador.config.exception.CartaoInexistenteException;

@Service
public class BuscarSaldoService implements BuscadorSaldo {
    private @Autowired CartaoRepository cartaoRepository;

    @Override
    public Double busca(String numeroCartao) throws CartaoInexistenteException {
        Optional<Cartao> cartao = cartaoRepository.findByNumeroCartao(numeroCartao);
        try {
            cartao.orElseThrow();
        } catch (NoSuchElementException e) {
            throw new CartaoInexistenteException();
        }
        return cartao.get().getSaldo();
    }
}
