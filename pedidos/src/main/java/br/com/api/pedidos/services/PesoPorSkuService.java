package br.com.api.pedidos.services;

import br.com.api.pedidos.domain.PesoPorSku;
import br.com.api.pedidos.repositories.PesoPorSkuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
