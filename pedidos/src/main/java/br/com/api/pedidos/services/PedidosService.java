package br.com.api.pedidos.services;

import br.com.api.pedidos.domain.Pedidos;
import br.com.api.pedidos.repositories.PedidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidosService {
    @Autowired
    private PedidosRepository repository;

    public void save(Pedidos pedido) {
        repository.save(pedido);
    }
}
