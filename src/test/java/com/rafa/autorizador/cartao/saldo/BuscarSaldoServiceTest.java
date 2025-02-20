package com.rafa.autorizador.cartao.saldo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rafa.autorizador.cartao.Cartao;
import com.rafa.autorizador.cartao.CartaoRepository;
import com.rafa.autorizador.config.exception.CartaoInexistenteException;

@ExtendWith(MockitoExtension.class)
public class BuscarSaldoServiceTest {
    private Cartao cartao;
    private String numeroCartao;
    private @Mock CartaoRepository cartaoRepository;
    private @InjectMocks BuscarSaldoService buscarSaldoService;
    
    @BeforeEach
    public void setUp() {
        numeroCartao = "1616161616161616";
        cartao = Cartao.builder().numeroCartao(numeroCartao).saldo(500.0).build();
    }

    @Test
    public void testBuscaSaldo_Success() throws CartaoInexistenteException {
        when(cartaoRepository.findByNumeroCartao(anyString())).thenReturn(Optional.of(cartao));

        Double saldo = buscarSaldoService.busca(numeroCartao);
        assertEquals(500.0, saldo);
    }

    @Test
    public void testBuscaSaldo_CartaoInexistenteException() {
        when(cartaoRepository.findByNumeroCartao(anyString())).thenReturn(Optional.empty());

        assertThrows(CartaoInexistenteException.class,
                () -> buscarSaldoService.busca(numeroCartao));
    }
}