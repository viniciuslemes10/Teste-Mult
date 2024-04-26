package br.com.api.pedidos.services;

import br.com.api.pedidos.repository.PedidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidosService {
    @Autowired
    private PedidosRepository repository;
}
