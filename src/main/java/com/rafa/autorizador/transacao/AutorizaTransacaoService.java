package com.rafa.autorizador.transacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafa.autorizador.cartao.exception.modelo.CartaoInexistenteException;
import com.rafa.autorizador.cartao.exception.modelo.CartaoInexistenteParaTransacaoException;
import com.rafa.autorizador.cartao.exception.modelo.SaldoInsuficienteException;
import com.rafa.autorizador.cartao.exception.modelo.SenhaInvalidaException;
import com.rafa.autorizador.cartao.modelo.Cartao;
import com.rafa.autorizador.cartao.modelo.CartaoRepository;

@Service
public class AutorizaTransacaoService implements AutorizadorTransacao {
    private @Autowired CartaoRepository cartaoRepository;

    @Override
    public String autoriza(TransacaoRecord transacao)
            throws CartaoInexistenteException, SaldoInsuficienteException, SenhaInvalidaException {

        Cartao cartao = cartaoRepository.findByNumeroCartao(transacao.numeroCartao())
                .orElseThrow(CartaoInexistenteParaTransacaoException::new);

        cartao.valida(transacao.senha())
                .orElseThrow(SenhaInvalidaException::new);

        cartao.valida(transacao.valor())
                .orElseThrow(SaldoInsuficienteException::new);

        cartao.subtraiSaldo(transacao.valor());
        cartaoRepository.save(cartao);

        return "OK";
    }
}
