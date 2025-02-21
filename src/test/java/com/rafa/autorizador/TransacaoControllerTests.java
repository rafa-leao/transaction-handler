package com.rafa.autorizador;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafa.autorizador.transacao.AutorizadorTransacao;
import com.rafa.autorizador.transacao.TransacaoRecord;
import com.rafa.autorizador.transacao.exception.modelo.CartaoInexistenteParaTransacaoException;
import com.rafa.autorizador.transacao.exception.modelo.SaldoInsuficienteException;
import com.rafa.autorizador.transacao.exception.modelo.SenhaInvalidaException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TransacaoControllerTests {
    private MockMvc mvc;

    private String senha;
    private BigDecimal valor;
    private String numeroCartao;
    private TransacaoRecord transacao;

    private @Autowired WebApplicationContext context;
    private @MockitoBean AutorizadorTransacao autorizadorTransacao;

    @BeforeEach
    public void setUp() {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        senha = "4444";
        numeroCartao = "1616161616161616";
        valor = BigDecimal.valueOf(500.00);

        // Não é necessário que TransacaoRecord tenha valores alterados para lançar
        // exeções
        // Os valores são úteis apenas para o Bean Validation
        transacao = new TransacaoRecord(numeroCartao, senha, valor);
    }

    @Test
    void testaAutorizacaoDeTransacao_Successo() throws Exception {
        when(autorizadorTransacao.autoriza(any(TransacaoRecord.class))).thenReturn("OK");
        assure(status().isCreated());
    }

    @Test
    void testaAutorizacaoDeTransacao_CartaoInexistenteParaTransacaoException() throws Exception {
        doThrow(new CartaoInexistenteParaTransacaoException())
                .when(autorizadorTransacao).autoriza(any(TransacaoRecord.class));
        assure(status().isUnprocessableEntity());
    }

    @Test
    void testaAutorizacaoDeTransacao_SenhaInvalidaException() throws Exception {
        doThrow(new SenhaInvalidaException())
                .when(autorizadorTransacao).autoriza(any(TransacaoRecord.class));
        assure(status().isUnprocessableEntity());
    }

    @Test
    void testaAutorizacaoDeTransacao_SaldoInsuficienteException() throws Exception {
        doThrow(new SaldoInsuficienteException())
                .when(autorizadorTransacao).autoriza(any(TransacaoRecord.class));
        assure(status().isUnprocessableEntity());
    }

    private ResultActions assure(ResultMatcher condicao) throws Exception {
        return mvc.perform(post("/transacoes")
                .with(httpBasic("username", "password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(transacao)))
                .andExpect(condicao);
    }
}
