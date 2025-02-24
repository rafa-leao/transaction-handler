package com.rafa.autorizador.cartao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.rafa.autorizador.cartao.exception.modelo.CartaoExistenteException;
import com.rafa.autorizador.cartao.modelo.Cartao;
import com.rafa.autorizador.cartao.modelo.CartaoRecord;
import com.rafa.autorizador.cartao.modelo.CartaoRepository;

@ExtendWith(MockitoExtension.class)
public class CriarCartaoServiceTests {
    private CartaoRecord cartaoRecord;
    private @Mock CartaoRepository repository;
    private @InjectMocks CriarCartaoService cartao;

    @BeforeEach
    public void setUp() {
        cartaoRecord = new CartaoRecord("1616161616161616", "4444");
    }

    @Test
    void testaCriacaoCartao_Successo() throws CartaoExistenteException {
        assertEquals(cartaoRecord, cartao.cria(cartaoRecord));
    }

    @Test
    void testaCriacaoCartao_CartaoExistenteException() {
        doThrow(DataIntegrityViolationException.class)
                .when(repository).save(any(Cartao.class));

        assertThrows(CartaoExistenteException.class,
                () -> cartao.cria(cartaoRecord));
    }
}
