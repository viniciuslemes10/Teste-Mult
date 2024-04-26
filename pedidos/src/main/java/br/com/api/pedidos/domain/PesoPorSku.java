package br.com.api.pedidos.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "peso_por_sku")
@Table(name = "peso_por_sku")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class PesoPorSku {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;
    private int pesoEmGramas;
}
