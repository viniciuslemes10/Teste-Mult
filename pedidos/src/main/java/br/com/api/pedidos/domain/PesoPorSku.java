package br.com.api.pedidos.domain;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "peso_por_sku_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "sku")
    private String sku;

    @Schema(description = "peso_em_gramas")
    private Double pesoEmGramas;

    public PesoPorSku(Map.Entry<String, Double> entry) {
        this.sku = entry.getKey();
        this.pesoEmGramas = entry.getValue();
    }
}
