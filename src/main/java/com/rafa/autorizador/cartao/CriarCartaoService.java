package com.rafa.autorizador.cartao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rafa.autorizador.cartao.exception.modelo.CartaoExistenteException;
import com.rafa.autorizador.cartao.modelo.Cartao;
import com.rafa.autorizador.cartao.modelo.CartaoRecord;
import com.rafa.autorizador.cartao.modelo.CartaoRepository;

@Service
@Transactional
public class CriarCartaoService implements CriadorCartao {
    private @Autowired CartaoRepository cartaoRepository;
    
    @Override
    public CartaoRecord cria(CartaoRecord cartaoRecord) throws CartaoExistenteException {
        try {
            cartaoRepository.save(Cartao.converte(cartaoRecord));
        } catch (DataIntegrityViolationException e) {
            throw new CartaoExistenteException(cartaoRecord);
        }
        return cartaoRecord;
    }
}
