package br.com.api.pedidos.repository;

import br.com.api.pedidos.domain.PesoPorSku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PesoPorSkuRepository extends JpaRepository<PesoPorSku, Long> {
}
