package br.com.api.pedidos.services;

import br.com.api.pedidos.domain.Caixas;
import br.com.api.pedidos.repositories.CaixaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaixaService {
    @Autowired
    private CaixaRepository repository;

    public void save(Caixas caixa) {
        repository.save(caixa);
    }
}
