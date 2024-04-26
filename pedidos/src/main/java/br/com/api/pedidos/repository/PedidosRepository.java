package br.com.api.pedidos.repository;

import br.com.api.pedidos.domain.Pedidos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidosRepository extends JpaRepository<Pedidos, Long> {
}
