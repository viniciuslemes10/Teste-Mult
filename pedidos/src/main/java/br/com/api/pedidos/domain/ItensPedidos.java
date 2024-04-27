package br.com.api.pedidos.domain;

import br.com.api.pedidos.records.ItemDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "itenspedidos")
@Table(name = "itenspedidos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItensPedidos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "caixas_id")
    private Caixas caixa;

    private String sku;
    private Integer quantidade;

    public ItensPedidos(ItemDTO itemDTO, Caixas caixa) {
        this.sku = itemDTO.sku();
        this.quantidade = itemDTO.qtd();
        this.caixa = caixa;
    }
}
