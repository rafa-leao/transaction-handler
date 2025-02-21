package com.rafa.autorizador;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rafa.autorizador.cartao.modelo.CartaoRecord;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class BeanValidationTests {
    private Validator validator;
    private String numeroCartao;
    private String senha;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        numeroCartao = "1616161616161616";
        senha = "4444";
    }

    @Test
    void testaBeanValidation_valido() {
        CartaoRecord cartao = new CartaoRecord(numeroCartao, senha);
        assertTrue(validator.validate(cartao).isEmpty());
    }

    @Test
    void testaBeanValidation_senhaInvalida() {
        CartaoRecord cartao = new CartaoRecord(numeroCartao, "333");
        assertFalse(validator.validate(cartao).isEmpty());
    }

    @Test
    void testaBeanValidation_cartaoInvalido() {
        CartaoRecord cartao = new CartaoRecord("151515151515151", senha);
        assertFalse(validator.validate(cartao).isEmpty());
    }
}
