package br.com.api.pedidos.services;

import br.com.api.pedidos.domain.Caixas;
import br.com.api.pedidos.domain.ItensPedidos;
import br.com.api.pedidos.domain.Pedidos;
import br.com.api.pedidos.records.ItemDTO;
import br.com.api.pedidos.records.PedidoCompletoDTO;
import br.com.api.pedidos.repositories.CaixaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CaixaService {
    @Autowired
    private CaixaRepository repository;

    @Autowired
    private ItensPedidosService itensPedidosService;

    public void save(Caixas caixa) {
        repository.save(caixa);
    }

    public List<Caixas> processarCaixas(Pedidos pedido, Map<String, List<ItemDTO>> caixasMap, PedidoCompletoDTO dto) {
        List<Caixas> caixas = new ArrayList<>();
        for (Map.Entry<String, List<ItemDTO>> caixaEntry : caixasMap.entrySet()) {
            Caixas caixa = criarCaixa(pedido);
            List<ItensPedidos> itensPedidos = itensPedidosService.processarItens(caixa, caixaEntry.getValue());
            caixa.setItensPedidos(itensPedidos);
            caixas.add(caixa);
        }
        return caixas;
    }

    @Transactional
    private Caixas criarCaixa(Pedidos pedido) {
        Caixas caixa = new Caixas(pedido);
        return repository.save(caixa);
    }

    public List<Object[]> getTotalWeightAndQuantityByCaixa() {
        return repository.getTotalWeightAndQuantityByCaixa();
    }
}
