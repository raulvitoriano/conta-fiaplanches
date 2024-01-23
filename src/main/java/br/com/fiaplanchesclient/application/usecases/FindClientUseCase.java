package br.com.fiaplanchesclient.application.usecases;

import br.com.fiaplanchesclient.application.ports.out.ClientRepositoryPortOut;
import br.com.fiaplanchesclient.infra.dto.ClientDto;

public class FindClientUseCase {

    private final ClientRepositoryPortOut clientRepositoryPortOut;;

    public FindClientUseCase(ClientRepositoryPortOut clientRepositoryPortOut) {
        this.clientRepositoryPortOut = clientRepositoryPortOut;
    }

    public ClientDto procuraPorCpf(String cpf) {
        return clientRepositoryPortOut.findClientByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente nao localizado"));
    }
}
