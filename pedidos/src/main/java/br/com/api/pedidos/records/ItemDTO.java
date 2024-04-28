package br.com.api.pedidos.records;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record ItemDTO(
        @Schema(description = "sku", example = "pendrive")
        @NotBlank
        String sku,
        @Schema(description = "qtd", example = "3")
        @NotBlank
        Integer qtd
) {
}
