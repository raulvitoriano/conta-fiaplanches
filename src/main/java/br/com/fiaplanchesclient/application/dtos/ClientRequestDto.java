package br.com.fiaplanchesclient.application.dtos;

import br.com.fiaplanchesclient.infra.dto.ClientDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record ClientRequestDto(
        @CPF(message = "CPF informado invalido")
        @NotBlank(message = "Número do CPF não pode ser vazio")
        String cpf,
        @Size(max = 100)
        @NotBlank(message = "Nome não pode ser vazio")
        String nome
) {

    public ClientDto toClienteDto() {
        return new ClientDto(null, this.cpf, this.nome);
    }
}
