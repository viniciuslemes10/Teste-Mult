package br.com.api.pedidos.records;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;

public record PedidoCompletoDTO(
        @Schema(description = "Mapa de pedidos", example = "{\"pedido1\": {\"caixa1\": [{\"sku\":\"pendrive\",\"qtd\":3}]}}")
        @JsonProperty("pedidos")
        @NotNull
        Map<String, Map<String, List<ItemDTO>>> pedidos,
        @Schema(description = "Mapa de peso por SKU", example = "{\"pendrive\": 50, \"mouse\": 210, \"keyboard\": 730, \"monitor\": 3100}")
        @JsonProperty("pesoPorSku")
        @NotNull
        Map<String, Double> pesoPorSku
) {
    public Map<String, Map<String, List<ItemDTO>>> pedidos() {
        return pedidos;
    }

    public Map<String, Double> pesoPorSku() {
        return pesoPorSku;
    }
}
