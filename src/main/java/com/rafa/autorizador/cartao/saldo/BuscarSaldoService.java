package com.rafa.autorizador.cartao.saldo;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafa.autorizador.cartao.Cartao;
import com.rafa.autorizador.cartao.CartaoRepository;
import com.rafa.autorizador.config.exception.CartaoInexistenteException;

@Service
public class BuscarSaldoService implements BuscadorSaldo {
    private @Autowired CartaoRepository cartaoRepository;

    @Override
    public BigDecimal busca(String numeroCartao) throws CartaoInexistenteException {
        Cartao cartao = cartaoRepository.findByNumeroCartao(numeroCartao)
                .orElseThrow(CartaoInexistenteException::new);
        return cartao.getSaldo();
    }
}
