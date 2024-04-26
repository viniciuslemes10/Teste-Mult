package br.com.api.pedidos.services;

import br.com.api.pedidos.repository.CaixaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaixaService {
    @Autowired
    private CaixaRepository repository;
}
