package com.rafa.autorizador.transacao;

import java.math.BigDecimal;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record TransacaoRecord(
        @Size(min = 16, max = 16) String numeroCartao,
        @Size(min = 4) String senha,
        @Positive BigDecimal valor) {
}
