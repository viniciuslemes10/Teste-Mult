package br.com.api.pedidos.controllers;

import br.com.api.pedidos.domain.Caixas;
import br.com.api.pedidos.domain.ItensPedidos;
import br.com.api.pedidos.domain.Pedidos;
import br.com.api.pedidos.domain.PesoPorSku;
import br.com.api.pedidos.records.ItemDTO;
import br.com.api.pedidos.records.PedidoCompletoDTO;
import br.com.api.pedidos.services.CaixaService;
import br.com.api.pedidos.services.ItensPedidosService;
import br.com.api.pedidos.services.PedidosService;
import br.com.api.pedidos.services.PesoPorSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pedidos")
public class PedidosController {
    @Autowired
    private ItensPedidosService itensPedidosService;

    @Autowired
    private PedidosService pedidosService;

    @Autowired
    private CaixaService caixaService;

    @Autowired
    private PesoPorSkuService pesoPorSkuService;

    @PostMapping
    public ResponseEntity<String> createPedido(@RequestBody PedidoCompletoDTO dto) {
        try {
            Map<String, Double> pesoPorSkuMap = new HashMap<>();
            createPesoPorSku(pesoPorSkuMap, dto);

            int totalProdutos = 0;
            double pesoTotal = 0.0;

            for (Map.Entry<String, Map<String, List<ItemDTO>>> pedidoEntry : dto.pedidos().entrySet()) {
                Pedidos pedido = new Pedidos();
                pedidosService.save(pedido);

                List<Caixas> caixas = new ArrayList<>();

                for (Map.Entry<String, List<ItemDTO>> caixaEntry : pedidoEntry.getValue().entrySet()) {
                    Caixas caixa = new Caixas(pedido);
                    caixaService.save(caixa);

                    List<ItensPedidos> itens = new ArrayList<>();
                    for (ItemDTO itemDTO : caixaEntry.getValue()) {
                        itens = createListItensPedidos(itens, caixa, itemDTO);

                        totalProdutos += itemDTO.qtd();
                        if (dto.pesoPorSku().containsKey(itemDTO.sku())) {
                            pesoTotal += dto.pesoPorSku().get(itemDTO.sku()) * itemDTO.qtd();
                        }
                    }
                    caixa.setItensPedidos(itens);
                    caixas.add(caixa);
                }
                pedido.setCaixas(caixas);
            }

            String resposta = "Pedido Criado. Total de Produtos: " + totalProdutos + ", Peso Total: " + pesoTotal + " gramas";
            return ResponseEntity.ok(resposta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar pedidos: " + e.getMessage());
        }
    }

    private void createPesoPorSku(Map<String, Double> pesoPorSkuMap, PedidoCompletoDTO dto) {
        for (Map.Entry<String, Double> entry : dto.pesoPorSku().entrySet()) {
            pesoPorSkuMap.put(entry.getKey(), entry.getValue());
            PesoPorSku pesoPorSku = new PesoPorSku(entry);
            pesoPorSkuService.save(pesoPorSku);
        }
    }

    private List<ItensPedidos> createListItensPedidos(List<ItensPedidos> itens, Caixas caixa, ItemDTO itemDTO) {
        ItensPedidos itemPedido = new ItensPedidos(itemDTO, caixa);
        itens.add(itemPedido);
        itensPedidosService.save(itens);
        return itens;
    }
}
