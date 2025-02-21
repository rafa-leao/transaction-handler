package com.rafa.autorizador;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafa.autorizador.cartao.CriadorCartao;
import com.rafa.autorizador.cartao.exception.modelo.CartaoExistenteException;
import com.rafa.autorizador.cartao.exception.modelo.CartaoInexistenteException;
import com.rafa.autorizador.cartao.modelo.CartaoRecord;
import com.rafa.autorizador.cartao.saldo.BuscadorSaldo;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AutorizadorControllerTests {
    private MockMvc mvc;
    private String numeroCartao;
    private CartaoRecord cartaoRecord;

    private @Autowired WebApplicationContext context;
    private @MockitoBean CriadorCartao criadorcartao;
    private @MockitoBean BuscadorSaldo buscadorSaldo;

    @BeforeEach
    public void setUp() {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        numeroCartao = "1616161616161616";
        cartaoRecord = new CartaoRecord(this.numeroCartao, "4444");
    }

    @Test
    void testaCriacaoDeCartao_Successo() throws Exception {
        when(criadorcartao.cria(any(CartaoRecord.class))).thenReturn(this.cartaoRecord);
        assure(post("/cartoes"), status().isCreated(), this.cartaoRecord);
    }

    @Test
    void testaCriacaoDeCartao_CartaoExistenteException() throws Exception {
        doThrow(new CartaoExistenteException(this.cartaoRecord))
                .when(criadorcartao).cria(any(CartaoRecord.class));
        assure(post("/cartoes"), status().isUnprocessableEntity(), this.cartaoRecord);
    }

    @Test
    void testaValidacaoDeCampos_BadRequest() throws Exception {
        assure(post("/cartoes"), status().isBadRequest(), new CartaoRecord(this.numeroCartao, "333"));
    }

    @Test
    void testaBuscaDeSaldo_Sucesso() throws Exception {
        when(buscadorSaldo.busca(anyString())).thenReturn(BigDecimal.valueOf(500.00));

        assure(get("/cartoes/{numeroCartao}", this.numeroCartao), status().isOk(), cartaoRecord);
    }

    @Test
    void testaBuscaDeSaldo_NotFound() throws Exception {
        doThrow(new CartaoInexistenteException())
                .when(buscadorSaldo).busca(anyString());
        assure(get("/cartoes/{numeroCartao}", this.numeroCartao), status().isNotFound(), cartaoRecord);
    }

    @Test
    void naoAutenticado_Unauthorized() throws Exception {
        mvc.perform(post("/cartoes")).andExpect(status().isUnauthorized());
    }

    private ResultActions assure(MockHttpServletRequestBuilder metodo, ResultMatcher condicao, CartaoRecord body)
            throws Exception {
        return mvc.perform(metodo
                .with(httpBasic("username", "password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(body)))
                .andExpect(condicao);
    }
}
