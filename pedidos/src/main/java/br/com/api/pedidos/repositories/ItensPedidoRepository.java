package br.com.api.pedidos.repositories;

import br.com.api.pedidos.domain.ItensPedidos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItensPedidoRepository extends JpaRepository<ItensPedidos, Long> {
}
