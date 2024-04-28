package br.com.api.pedidos.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pedidos {
    @Schema(description = "pedidos_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "caixas_id")
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<Caixas> caixas;

}
