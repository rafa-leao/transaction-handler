package com.rafa.autorizador.transacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rafa.autorizador.cartao.modelo.Cartao;
import com.rafa.autorizador.cartao.modelo.CartaoRepository;
import com.rafa.autorizador.transacao.exception.modelo.CartaoInexistenteParaTransacaoException;
import com.rafa.autorizador.transacao.exception.modelo.SaldoInsuficienteException;
import com.rafa.autorizador.transacao.exception.modelo.SenhaInvalidaException;
import com.rafa.autorizador.transacao.exception.modelo.TransacaoException;

@Service
public class AutorizaTransacaoService implements AutorizadorTransacao {
	private @Autowired CartaoRepository cartaoRepository;

	@Override
	@Transactional
	public String autoriza(TransacaoRecord transacao) throws TransacaoException {

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
