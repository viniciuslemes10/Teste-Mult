package br.com.api.pedidos.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "pedidos")
@Table(name = "pedidos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pedidos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<Caixa> caixas;

}
