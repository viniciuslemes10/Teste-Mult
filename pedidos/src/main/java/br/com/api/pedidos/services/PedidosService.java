package br.com.api.pedidos.services;

import br.com.api.pedidos.domain.Pedidos;
import br.com.api.pedidos.repositories.PedidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PedidosService {
    @Autowired
    private PedidosRepository repository;

    public void save(Pedidos pedido) {
        repository.save(pedido);
    }

    public List<Object[]> findAllPedidosWithDetailsAndPesosPorSku() {
        List<Object[]> listDetailsPedidos = repository.findAllPedidosWithDetailsAndPesosPorSku();
        return listDetailsPedidos;
    }

    public Pedidos criarPedido() {
        Pedidos pedido = new Pedidos();
        return repository.save(pedido);
    }

    public Map<String, Object> createPedidoMap(Object[] pedidoDetalhado) {
        Long pedidoId = (Long) pedidoDetalhado[0];
        Map<String, Object> pedidoMap = new HashMap<>();
        pedidoMap.put("pedido_id", pedidoId);
        pedidoMap.put("itens", new ArrayList<>());
        return pedidoMap;
    }

    public Map<String, Object> createItemPedidoMap(Object[] pedidoDetalhado) {
        Long caixaId = (Long) pedidoDetalhado[1];
        Long itemPedidoId = (Long) pedidoDetalhado[2];
        String sku = (String) pedidoDetalhado[3];
        Integer quantidade = (Integer) pedidoDetalhado[4];
        Double pesoPorSku = (Double) pedidoDetalhado[5];

        Map<String, Object> itemPedidoMap = new HashMap<>();
        itemPedidoMap.put("caixa_id", caixaId);
        itemPedidoMap.put("item_pedido_id", itemPedidoId);
        itemPedidoMap.put("sku", sku);
        itemPedidoMap.put("quantidade", quantidade);
        itemPedidoMap.put("peso_por_sku", pesoPorSku);
        return itemPedidoMap;
    }

    public void addToResponseList(List<Map<String, Object>> responseList, Map<String, Object> pedidoMap, Map<String, Object> itemPedidoMap) {
        Long novoPedidoId = (Long) pedidoMap.get("pedido_id");
        for (Map<String, Object> pedidoExistenteMap : responseList) {
            Long pedidoExistenteId = (Long) pedidoExistenteMap.get("pedido_id");
            if (pedidoExistenteId.equals(novoPedidoId)) {
                ((List<Map<String, Object>>) pedidoExistenteMap.get("itens")).add(itemPedidoMap);
                return;
            }
        }
        ((List<Map<String, Object>>) pedidoMap.get("itens")).add(itemPedidoMap);
        responseList.add(pedidoMap);
    }

    public List<Object[]> findTotalQuantityAndWeightDetails() {
        return repository.getTotalQuantityAndWeightDetails();
    }

    public List<Object[]> findTotalWeightAndQuantityByPedido() {
        return repository.getTotalWeightAndQuantityByPedido();
    }
}
