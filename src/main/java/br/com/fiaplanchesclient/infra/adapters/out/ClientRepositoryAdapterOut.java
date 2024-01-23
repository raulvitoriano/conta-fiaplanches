package br.com.fiaplanchesclient.infra.adapters.out;

import br.com.fiaplanchesclient.application.ports.out.ClientRepositoryPortOut;
import br.com.fiaplanchesclient.infra.dto.ClientDto;
import br.com.fiaplanchesclient.infra.repository.PostGresClienteRepository;
import br.com.fiaplanchesclient.infra.repository.entity.ClientEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClientRepositoryAdapterOut implements ClientRepositoryPortOut {

    private final PostGresClienteRepository postGresClienteRepository;

    public ClientRepositoryAdapterOut(PostGresClienteRepository postGresClienteRepository) {
        this.postGresClienteRepository = postGresClienteRepository;
    }

    @Override
    public ClientDto saveClient(ClientDto clienteDto) {
        var clienteEntity = postGresClienteRepository.save(new ClientEntity(clienteDto));
        return clienteEntity.toClienteDto();
    }

    @Override
    public Optional<ClientDto> findClientByCpf(String cpf) {
        return postGresClienteRepository.findByCpf(cpf).map(ClientEntity::toClienteDto);
    }

    @Override
    public void deleteClient(String cpf) {
        postGresClienteRepository.deleteByCpf(cpf);
    }
}
