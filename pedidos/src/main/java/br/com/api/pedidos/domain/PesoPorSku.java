package br.com.api.pedidos.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Entity
@Table(name = "pesoporsku")
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
    private Double pesoEmGramas;

    public PesoPorSku(Map.Entry<String, Double> entry) {
        this.sku = entry.getKey();
        this.pesoEmGramas = entry.getValue();
    }
}
