package br.com.api.pedidos.services;

import br.com.api.pedidos.domain.ItensPedidos;
import br.com.api.pedidos.repositories.ItensPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItensPedidosService {
    @Autowired
    private ItensPedidoRepository repository;

    public List<ItensPedidos> save(List<ItensPedidos> itens) {
        return repository.saveAll(itens);
    }
}
