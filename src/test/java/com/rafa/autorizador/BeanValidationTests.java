package com.rafa.autorizador;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rafa.autorizador.cartao.CartaoRecord;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class BeanValidationTests {
    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testaBeanValidation_valido() {
        CartaoRecord cartao = new CartaoRecord("1616161616161616", "4444");
        assertTrue(validator.validate(cartao).isEmpty());
    }

    @Test
    void testaBeanValidation_senhaInvalida() {
        CartaoRecord cartao = new CartaoRecord("1616161616161616", "333");
        assertFalse(validator.validate(cartao).isEmpty());
    }

    @Test
    void testaBeanValidation_cartaoInvalido() {
        CartaoRecord cartao = new CartaoRecord("151515151515151", "4444");
        assertFalse(validator.validate(cartao).isEmpty());
    }
}
