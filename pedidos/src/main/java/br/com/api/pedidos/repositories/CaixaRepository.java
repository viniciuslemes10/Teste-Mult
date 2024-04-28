package br.com.api.pedidos.repositories;

import br.com.api.pedidos.domain.Caixas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaixaRepository extends JpaRepository<Caixas, Long> {
    @Query(value = "SELECT c.id AS caixa_id, " +
            "       SUM(ip.quantidade) AS quantidade_total, " +
            "       SUM(ip.quantidade * ps.peso_em_gramas) AS peso_caixa_total, " +
            "       GROUP_CONCAT(ip.sku) AS skus " +
            "FROM Caixas c " +
            "LEFT JOIN ItensPedidos ip ON c.id = ip.caixas_id " +
            "LEFT JOIN PesoPorSku ps ON ip.sku = ps.sku " +
            "GROUP BY c.id",
            nativeQuery = true)
    List<Object[]> getTotalWeightAndQuantityByCaixa();

}
