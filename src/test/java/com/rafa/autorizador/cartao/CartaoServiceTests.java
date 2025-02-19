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

import com.rafa.autorizador.cartao.exception.CartaoExistenteException;

@ExtendWith(MockitoExtension.class)
public class CartaoServiceTests {

    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private CartaoService cartaoService;

    private CartaoRecord cartaoRecord;

    @BeforeEach
    public void setUp() {
        cartaoRecord = new CartaoRecord("1616161616161616", "4444");
    }

    @Test
    void testCriaCartao_Success() throws CartaoExistenteException {
        assertEquals(cartaoRecord, cartaoService.cria(cartaoRecord));
    }

    @Test
    void testCriaCartao_CartaoExistenteException() {
        doThrow(DataIntegrityViolationException.class)
                .when(cartaoRepository).save(any(Cartao.class));

        assertThrows(CartaoExistenteException.class,
                () -> cartaoService.cria(cartaoRecord));
    }
}
