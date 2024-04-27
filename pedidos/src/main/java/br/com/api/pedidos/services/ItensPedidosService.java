package br.com.api.pedidos.services;

import br.com.api.pedidos.domain.Caixas;
import br.com.api.pedidos.domain.ItensPedidos;
import br.com.api.pedidos.records.ItemDTO;
import br.com.api.pedidos.repositories.ItensPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItensPedidosService {
    @Autowired
    private ItensPedidoRepository repository;

    public List<ItensPedidos> save(List<ItensPedidos> itens) {
        return repository.saveAll(itens);
    }

    public List<ItensPedidos> processarItens(Caixas caixa, List<ItemDTO> itemDTOs) {
        List<ItensPedidos> itensPedidos = new ArrayList<>();
        for (ItemDTO itemDTO : itemDTOs) {
            itensPedidos = createListItensPedidos(caixa, itemDTO);
        }
        return itensPedidos;
    }

    private List<ItensPedidos> createListItensPedidos(Caixas caixa, ItemDTO itemDTO) {
        List<ItensPedidos> itens = new ArrayList<>();
        ItensPedidos itemPedido = new ItensPedidos(itemDTO, caixa);
        itens.add(itemPedido);
        save(itens);
        return itens;
    }

    public List<ItensPedidos> getAllItensPedido() {
        return repository.findAll();
    }
}
