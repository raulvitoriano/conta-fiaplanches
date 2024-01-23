package br.com.fiaplanchesclient.infra.repository;

import br.com.fiaplanchesclient.infra.repository.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PostGresClienteRepository extends JpaRepository<ClientEntity, Long> {

    Optional<ClientEntity> findByCpf(String cpf);

    void deleteByCpf(String cpf);
}
