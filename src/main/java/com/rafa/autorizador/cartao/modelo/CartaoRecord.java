package com.rafa.autorizador.cartao.modelo;

import jakarta.validation.constraints.Size;

public record CartaoRecord(
                @Size(min = 16, max = 16) String numeroCartao,
                @Size(min = 4) String senha) {
}