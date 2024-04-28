package br.com.api.pedidos.domain;

import br.com.api.pedidos.records.ItemDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "itenspedidos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItensPedidos {
    @Schema(description = "itens_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "caixa_id")
    @ManyToOne
    @JoinColumn(name = "caixas_id")
    private Caixas caixa;

    @Schema(description = "sku")
    private String sku;

    @Schema(description = "qtd")
    private Integer quantidade;

    public ItensPedidos(ItemDTO itemDTO, Caixas caixa) {
        this.sku = itemDTO.sku();
        this.quantidade = itemDTO.qtd();
        this.caixa = caixa;
    }
}
