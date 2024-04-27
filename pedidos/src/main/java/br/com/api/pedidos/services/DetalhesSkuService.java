package br.com.api.pedidos.services;

import br.com.api.pedidos.domain.ItensPedidos;
import br.com.api.pedidos.domain.PesoPorSku;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DetalhesSkuService {
    public Map<String, Double> mapearPesosPorSku(List<PesoPorSku> todosPesoPorSku) {
        Map<String, Double> pesoPorSkuMap = new HashMap<>();
        for (PesoPorSku pesoPorSku : todosPesoPorSku) {
            pesoPorSkuMap.put(pesoPorSku.getSku(), pesoPorSku.getPesoEmGramas());
        }
        return pesoPorSkuMap;
    }

    public Map<String, Integer> calcularQuantidadeTotalPorSku(List <ItensPedidos> todosItensPedido) {
        Map<String, Integer> quantidadePorSkuMap = new HashMap<>();
        for (ItensPedidos itensPedidos : todosItensPedido) {
            String sku = itensPedidos.getSku();
            quantidadePorSkuMap.put(sku, quantidadePorSkuMap.getOrDefault(sku, 0) + itensPedidos.getQuantidade());
        }
        return quantidadePorSkuMap;
    }

    public List<Map<String, Object>> criarDetalhesPorSku(Map<String, Double> pesoPorSkuMap,
                                                         Map<String, Integer> quantidadePorSkuMap) {
        List<Map<String, Object>> detailsList = new ArrayList<>();
        for (Map.Entry<String, Double> entry : pesoPorSkuMap.entrySet()) {
            String sku = entry.getKey();
            double pesoTotal = entry.getValue();
            int quantidadeTotal = quantidadePorSkuMap.getOrDefault(sku, 0);

            Map<String, Object> details = new HashMap<>();
            details.put("sku", sku);
            details.put("pesoTotal", pesoTotal);
            details.put("quantidadeTotal", quantidadeTotal);
            detailsList.add(details);
        }
        return detailsList;
    }
}
