package br.com.api.pedidos.controllers;

import br.com.api.pedidos.domain.Caixas;
import br.com.api.pedidos.domain.ItensPedidos;
import br.com.api.pedidos.domain.Pedidos;
import br.com.api.pedidos.domain.PesoPorSku;
import br.com.api.pedidos.exception.ViolationOfArgumentsException;
import br.com.api.pedidos.records.ItemDTO;
import br.com.api.pedidos.records.PedidoCompletoDTO;
import br.com.api.pedidos.services.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Criar um novo pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida")
    })
    @PostMapping
    @Transactional
    public ResponseEntity<String> createPedido(@RequestBody PedidoCompletoDTO dto, UriComponentsBuilder builder) {
        try {
            validarItensPedido(dto);
            pesoPorSkuService.createPesoPorSku(dto);
            int totalProdutos = 0;
            double pesoTotal = 0.0;
            List<Caixas> caixas;

            for (Map.Entry<String, Map<String, List<ItemDTO>>> pedidoEntry : dto.pedidos().entrySet()) {
                if (!pedidoEntry.getValue().isEmpty()) {
                    Pedidos pedido = pedidosService.criarPedido();
                    caixas = caixaService.processarCaixas(pedido, pedidoEntry.getValue(), dto);
                    pedido.setCaixas(caixas);

                    for (Map.Entry<String, List<ItemDTO>> caixaEntry : pedidoEntry.getValue().entrySet()) {
                        if (!caixaEntry.getValue().isEmpty()) {
                            for (ItemDTO itemDTO : caixaEntry.getValue()) {
                                totalProdutos += pesoPorSkuService.calcularTotalDeProdutos(itemDTO);
                                pesoTotal += pesoPorSkuService.calcularTotalDePeso(dto, itemDTO, 0);
                            }
                        }
                    }
                }
            }
            String resposta = "Pedido Criado. Total de Produtos: " + totalProdutos + ", Peso Total: " + pesoTotal + " gramas";
            URI uri = builder.buildAndExpand().toUri();
            return ResponseEntity.created(uri).body(resposta);
        } catch (ViolationOfArgumentsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar pedidos: " + e.getMessage());
        }
    }

    private void validarItensPedido(PedidoCompletoDTO dto) {
        for (Map.Entry<String, Map<String, List<ItemDTO>>> pedidoEntry : dto.pedidos().entrySet()) {
            for (Map.Entry<String, List<ItemDTO>> caixaEntry : pedidoEntry.getValue().entrySet()) {
                for (ItemDTO itemDTO : caixaEntry.getValue()) {
                    if ((itemDTO.qtd() == null || itemDTO.qtd() == 0) ||
                            (itemDTO.sku() == null || itemDTO.sku().isEmpty())) {
                        throw new ViolationOfArgumentsException();
                    }
                }
            }
        }

    }

    @Operation(summary = "Obter todos os pedidos com detalhes e pesos por SKU")
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

    @Operation(summary = "Obter a quantidade total e o peso total de todos os SKUs")
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getTotalQuantityAndWeightDetailsOfSku() {
        List <ItensPedidos> todosItensPedido = itensPedidosService.getAllItensPedido();
        List<PesoPorSku> todosPesoPorSku = pesoPorSkuService.getAllPesoPorSku();

        Map<String, Double> pesoPorSkuMap = detalhesSkuService.mapearPesosPorSku(todosPesoPorSku);

        Map<String, Integer> quantidadePorSkuMap = detalhesSkuService.calcularQuantidadeTotalPorSku(todosItensPedido);

        List<Map<String, Object>> detailsList = detalhesSkuService.criarDetalhesPorSku(pesoPorSkuMap, quantidadePorSkuMap);

        return ResponseEntity.ok(detailsList);
    }

    @Operation(summary = "Obter a quantidade total e o peso total de todos os pedidos")
    @GetMapping("/total-peso-quantidade")
    public ResponseEntity<Map<String, Object>> getTotalQuantityAndWeightDetails() {
        List<Object[]> resultList = pedidosService.findTotalQuantityAndWeightDetails();

        if(resultList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Object[] result = resultList.get(0);

        Map<String, Object> response = new HashMap<>();
        response.put("quantidade_total", result[0]);
        response.put("peso_total_gramas", result[1]);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obter o peso total e a quantidade total por caixa")
    @GetMapping("/total-peso-quantidade-por-caixa")
    public ResponseEntity<List<Map<String, Object>>> getTotalWeightAndQuantityByCaixa() {
        return getResponseEntity(caixaService.getTotalWeightAndQuantityByCaixa(),
                "caixa_id", "quantidade_total", "peso_total_gramas", "skus");
    }

    @Operation(summary = "Obter o peso total e a quantidade total por pedido")
    @GetMapping("total-peso-quantidade-por-pedido")
    public ResponseEntity<List<Map<String, Object>>> getTotalWeightAndQuantityByPedido() {
        return getResponseEntity(pedidosService.findTotalWeightAndQuantityByPedido(),
                "pedido_id", "peso_total_pedido", "caixas_id", "quantidade_total_itens", "skus_itens");
    }

    private ResponseEntity<List<Map<String, Object>>> getResponseEntity(List<Object[]> resultList, String... keys) {
        if (resultList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Map<String, Object>> responseList = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> response = new HashMap<>();
            for (int i = 0; i < keys.length; i++) {
                response.put(keys[i], result[i]);
            }
            responseList.add(response);
        }

        return ResponseEntity.ok(responseList);
    }
}
