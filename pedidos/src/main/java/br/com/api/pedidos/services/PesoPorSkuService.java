package br.com.api.pedidos.services;

import br.com.api.pedidos.domain.PesoPorSku;
import br.com.api.pedidos.records.ItemDTO;
import br.com.api.pedidos.records.PedidoCompletoDTO;
import br.com.api.pedidos.repositories.PesoPorSkuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PesoPorSkuService {
    @Autowired
    private PesoPorSkuRepository repository;

    public void save(PesoPorSku pesoPorSku) {
        List<PesoPorSku> pesoPorSkuList = repository.findAll();
        boolean skuExistente = false;

        for(PesoPorSku pesoSku : pesoPorSkuList) {
            if (pesoSku.getSku().equals(pesoPorSku.getSku())) {
                skuExistente = true;
                break;
            }
        }

        if (!skuExistente) {
            repository.save(pesoPorSku);
        } else {
            System.out.println("Já contém esse produto na tabela de pesoPorSku: " + pesoPorSku.getSku());
        }
    }

    @Transactional
    public Map<String, Double> createPesoPorSku(PedidoCompletoDTO dto) {
        Map<String, Double> pesoPorSkuMap = new HashMap<>();
        for (Map.Entry<String, Double> entry : dto.pesoPorSku().entrySet()) {
            pesoPorSkuMap.put(entry.getKey(), entry.getValue());
            PesoPorSku pesoPorSku = new PesoPorSku(entry);
            save(pesoPorSku);
        }
        return pesoPorSkuMap;
    }

    public double calcularTotalDePeso(PedidoCompletoDTO dto, ItemDTO itemDTO, double pesoTotal) {
        if (dto.pesoPorSku().containsKey(itemDTO.sku())) {
            pesoTotal += dto.pesoPorSku().get(itemDTO.sku()) * itemDTO.qtd();
            return pesoTotal;
        }
        return pesoTotal;
    }

    public int calcularTotalDeProdutos(ItemDTO itemDTO) {
        int totalProdutos = 0;
        totalProdutos += itemDTO.qtd();
        return totalProdutos;
    }

    public List<PesoPorSku> getAllPesoPorSku() {
        return repository.findAll();
    }
}
