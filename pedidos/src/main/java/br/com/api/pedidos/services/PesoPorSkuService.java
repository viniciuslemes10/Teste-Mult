package br.com.api.pedidos.services;

import br.com.api.pedidos.repository.PesoPorSkuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PesoPorSkuService {
    @Autowired
    private PesoPorSkuRepository repository;
}
