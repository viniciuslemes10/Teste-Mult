package br.com.api.pedidos.repositories;

import br.com.api.pedidos.domain.ItensPedidos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItensPedidoRepository extends JpaRepository<ItensPedidos, Long> {
}
