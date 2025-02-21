package com.rafa.autorizador.transacao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rafa.autorizador.cartao.modelo.Cartao;
import com.rafa.autorizador.cartao.modelo.CartaoRepository;
import com.rafa.autorizador.transacao.exception.modelo.CartaoInexistenteParaTransacaoException;
import com.rafa.autorizador.transacao.exception.modelo.SaldoInsuficienteException;
import com.rafa.autorizador.transacao.exception.modelo.SenhaInvalidaException;
import com.rafa.autorizador.transacao.exception.modelo.TransacaoException;

@ExtendWith(MockitoExtension.class)
public class AutorizaTransacaoServiceTest {
    private Cartao cartao;
    private TransacaoRecord transacao;

    private String senha;
    private BigDecimal valor;
    private String numeroCartao;

    private @Mock CartaoRepository cartaoRepository;
    private @InjectMocks AutorizaTransacaoService autorizaTransacaoService;

    @BeforeEach
    public void setUp() {
        senha = "4444";
        numeroCartao = "1616161616161616";
        valor = BigDecimal.valueOf(500.00);

        cartao = Cartao.builder()
                .numeroCartao(numeroCartao)
                .saldo(valor)
                .senha(senha).build();
    }

    @Test
    public void testCartaoInexistente() {
        when(cartaoRepository.findByNumeroCartao(anyString()))
                .thenReturn(Optional.empty());

        transacao = new TransacaoRecord("1616161616161615", senha, BigDecimal.ZERO);
        assertThrows(CartaoInexistenteParaTransacaoException.class, () -> {
            autorizaTransacaoService.autoriza(transacao);
        });
    }

    @Test
    public void testSenhaInvalida() {
        when(cartaoRepository.findByNumeroCartao(anyString()))
                .thenReturn(Optional.of(cartao));

        transacao = new TransacaoRecord(numeroCartao, "4443", valor);
        assertThrows(SenhaInvalidaException.class, () -> {
            autorizaTransacaoService.autoriza(transacao);
        });
    }

    @Test
    public void testSaldoInsuficiente() {
        when(cartaoRepository.findByNumeroCartao(anyString()))
                .thenReturn(Optional.of(cartao));

        transacao = new TransacaoRecord(numeroCartao, senha, BigDecimal.valueOf(600));
        assertThrows(SaldoInsuficienteException.class, () -> {
            autorizaTransacaoService.autoriza(transacao);
        });
    }

    @Test
    public void testTransacaoBemSucedida() throws TransacaoException {
        when(cartaoRepository.findByNumeroCartao(anyString())).thenReturn(Optional.of(cartao));

        transacao = new TransacaoRecord(numeroCartao, senha, valor);
        assertEquals("OK", autorizaTransacaoService.autoriza(transacao));
        assertEquals(BigDecimal.valueOf(0.0), cartao.getSaldo());
    }
}