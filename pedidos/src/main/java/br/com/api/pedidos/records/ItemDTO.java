package br.com.api.pedidos.records;

import jakarta.validation.constraints.NotBlank;

public record ItemDTO(
        @NotBlank
        String sku,
        @NotBlank
        Integer qtd
) {
}
