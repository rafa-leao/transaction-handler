package com.rafa.autorizador.cartao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.rafa.autorizador.cartao.exception.CartaoExistenteException;

@Service
public class CartaoService implements ManipuladorCartao {
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
