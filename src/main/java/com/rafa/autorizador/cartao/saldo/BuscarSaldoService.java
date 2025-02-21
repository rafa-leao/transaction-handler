package com.rafa.autorizador.cartao.saldo;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rafa.autorizador.cartao.exception.modelo.CartaoInexistenteException;
import com.rafa.autorizador.cartao.modelo.Cartao;
import com.rafa.autorizador.cartao.modelo.CartaoRepository;

@Service
public class BuscarSaldoService implements BuscadorSaldo {
    private @Autowired CartaoRepository cartaoRepository;

    @Override
    @Transactional(readOnly = true)
    public BigDecimal busca(String numeroCartao) throws CartaoInexistenteException {
        Cartao cartao = cartaoRepository.findByNumeroCartao(numeroCartao)
                .orElseThrow(CartaoInexistenteException::new);
        return cartao.getSaldo();
    }
}
