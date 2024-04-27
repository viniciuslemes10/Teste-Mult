package br.com.api.pedidos.controllers;

import br.com.api.pedidos.domain.Caixas;
import br.com.api.pedidos.domain.ItensPedidos;
import br.com.api.pedidos.domain.Pedidos;
import br.com.api.pedidos.domain.PesoPorSku;
import br.com.api.pedidos.records.ItemDTO;
import br.com.api.pedidos.records.PedidoCompletoDTO;
import br.com.api.pedidos.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

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

    @Autowired
    private DetalhesSkuService detalhesSkuService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> createPedido(@RequestBody PedidoCompletoDTO dto, UriComponentsBuilder builder) {
        try {
            pesoPorSkuService.createPesoPorSku(dto);
            int totalProdutos = 0;
            double pesoTotal = 0.0;
            List<Caixas> caixas;


            for (Map.Entry<String, Map<String, List<ItemDTO>>> pedidoEntry : dto.pedidos().entrySet()) {
                Pedidos pedido = pedidosService.criarPedido();
                caixas = caixaService.processarCaixas(pedido, pedidoEntry.getValue(), dto);
                pedido.setCaixas(caixas);

                for (Map.Entry<String, List<ItemDTO>> caixaEntry : pedidoEntry.getValue().entrySet()) {
                    for (ItemDTO itemDTO : caixaEntry.getValue()) {
                        totalProdutos += pesoPorSkuService.calcularTotalDeProdutos(itemDTO);
                        pesoTotal += pesoPorSkuService.calcularTotalDePeso(dto, itemDTO, 0);
                    }
                }
            }

            String resposta = "Pedido Criado. Total de Produtos: " + totalProdutos + ", Peso Total: " + pesoTotal + " gramas";
            URI uri = builder.buildAndExpand().toUri();
            return ResponseEntity.created(uri).body(resposta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar pedidos: " + e.getMessage());
        }
    }

    @GetMapping("/detalhes")
    public ResponseEntity<List<Map<String, Object>>> getAllPedidosWithDetailsAndPesosPorSku() {
        List<Object[]> pedidosDetalhados = pedidosService.findAllPedidosWithDetailsAndPesosPorSku();
        List<Map<String, Object>> responseList = new ArrayList<>();

        for (Object[] pedidoDetalhado : pedidosDetalhados) {
            Map<String, Object> pedidoMap = pedidosService.createPedidoMap(pedidoDetalhado);
            Map<String, Object> itemPedidoMap = pedidosService.createItemPedidoMap(pedidoDetalhado);
            pedidosService.addToResponseList(responseList, pedidoMap, itemPedidoMap);
        }

        return ResponseEntity.ok(responseList);
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getTotalQuantityAndWeightDetails() {
        List <ItensPedidos> todosItensPedido = itensPedidosService.getAllItensPedido();
        List<PesoPorSku> todosPesoPorSku = pesoPorSkuService.getAllPesoPorSku();

        Map<String, Double> pesoPorSkuMap = detalhesSkuService.mapearPesosPorSku(todosPesoPorSku);

        Map<String, Integer> quantidadePorSkuMap = detalhesSkuService.calcularQuantidadeTotalPorSku(todosItensPedido);

        List<Map<String, Object>> detailsList = detalhesSkuService.criarDetalhesPorSku(pesoPorSkuMap, quantidadePorSkuMap);

        return ResponseEntity.ok(detailsList);
    }
}
