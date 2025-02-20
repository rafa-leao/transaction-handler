package com.rafa.autorizador;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.rafa.autorizador.cartao.CartaoRecord;
import com.rafa.autorizador.cartao.ManipuladorCartao;
import com.rafa.autorizador.config.exception.CartaoExistenteException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AutorizadorControllerTests {
    private MockMvc mvc;
    private CartaoRecord cartaoRecord;

    private @Autowired WebApplicationContext context;
    private @MockitoBean ManipuladorCartao manipuladorCartao;


    @BeforeEach
    public void setUp() {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        cartaoRecord = new CartaoRecord("1616161616161616", "4444");
    }

    @Test
    void testAutorizaCartao_Success() throws Exception {
        when(manipuladorCartao.cria(any(CartaoRecord.class))).thenReturn(cartaoRecord);
        assure(status().isCreated());
    }

    @Test
    void testAutorizaCartao_CartaoExistenteException() throws Exception {
        doThrow(new CartaoExistenteException(cartaoRecord))
                .when(manipuladorCartao).cria(any(CartaoRecord.class));
        assure(status().isUnprocessableEntity());
    }

    private ResultActions assure(ResultMatcher rm) throws Exception {
        return mvc.perform(post("/cartoes")
                .with(httpBasic("username", "password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(cartaoRecord)))
                .andExpect(rm);
    }
}
