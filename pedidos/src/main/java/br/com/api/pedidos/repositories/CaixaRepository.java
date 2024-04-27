package br.com.api.pedidos.repositories;

import br.com.api.pedidos.domain.Caixas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CaixaRepository extends JpaRepository<Caixas, Long> {
}
