package br.com.api.pedidos.domain;

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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedidos_id")
    private Pedidos pedido;

    @OneToMany(mappedBy = "caixa", cascade = CascadeType.ALL)
    private List<ItensPedidos> itensPedidos;

    public Caixas(Pedidos pedido) {
        this.pedido = pedido;
    }
}
