package br.com.api.pedidos.records;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;

public record PedidoCompletoDTO(
        @NotNull
        Map<String, Map<String, List<ItemDTO>>> pedidos,
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
