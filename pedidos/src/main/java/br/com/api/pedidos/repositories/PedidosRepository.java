package br.com.api.pedidos.repositories;

import br.com.api.pedidos.domain.Pedidos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidosRepository extends JpaRepository<Pedidos, Long> {
    @Query(value = "SELECT p.id AS pedido_id, c.id AS caixa_id, ip.id AS item_pedido_id, ip.sku, ip.quantidade, ps.peso_em_gramas AS peso_por_sku " +
            "FROM Pedidos p " +
            "LEFT JOIN Caixas c ON p.id = c.pedidos_id " +
            "LEFT JOIN ItensPedidos ip ON c.id = ip.caixas_id " +
            "LEFT JOIN PesoPorSku ps ON ip.sku = ps.sku",
            nativeQuery = true)
    List<Object[]> findAllPedidosWithDetailsAndPesosPorSku();
}
