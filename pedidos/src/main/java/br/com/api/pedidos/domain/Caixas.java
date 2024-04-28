package br.com.api.pedidos.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "caixas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Caixas {
    @Schema(description = "caixa_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "pedido_id")
    @ManyToOne
    @JoinColumn(name = "pedidos_id")
    private Pedidos pedido;

    @Schema(description = "itens_id")
    @OneToMany(mappedBy = "caixa", cascade = CascadeType.ALL)
    private List<ItensPedidos> itensPedidos;

    public Caixas(Pedidos pedido) {
        this.pedido = pedido;
    }
}
