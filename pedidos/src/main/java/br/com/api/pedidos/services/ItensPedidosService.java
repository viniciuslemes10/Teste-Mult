package br.com.api.pedidos.services;

import br.com.api.pedidos.repository.ItensPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItensPedidosService {
    @Autowired
    private ItensPedidoRepository repository;
}
