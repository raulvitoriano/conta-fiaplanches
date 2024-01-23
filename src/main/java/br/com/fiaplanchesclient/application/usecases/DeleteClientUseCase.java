package br.com.fiaplanchesclient.application.usecases;

import br.com.fiaplanchesclient.application.ports.out.ClientRepositoryPortOut;
import jakarta.persistence.EntityNotFoundException;

public class DeleteClientUseCase {


    private final ClientRepositoryPortOut clientRepositoryPortOut;

    public DeleteClientUseCase(ClientRepositoryPortOut clientRepositoryPortOut) {
        this.clientRepositoryPortOut = clientRepositoryPortOut;
    }

    public void remove(String cpf) {
        clientRepositoryPortOut.findClientByCpf(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
        clientRepositoryPortOut.deleteClient(cpf);
    }
}
