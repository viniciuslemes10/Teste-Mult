package br.com.api.pedidos.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "itens_pedidos")
@Table(name = "itens_pedidos")
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
    private Caixa caixa;

    private String sku;
    private Integer quantidade;
    private Double peso;
}
