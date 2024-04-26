package br.com.api.pedidos.repository;

import br.com.api.pedidos.domain.Caixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CaixaRepository extends JpaRepository<Caixa, Long> {
}
