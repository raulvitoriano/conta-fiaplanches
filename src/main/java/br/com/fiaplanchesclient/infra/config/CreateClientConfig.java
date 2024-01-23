package br.com.fiaplanchesclient.infra.config;

import br.com.fiaplanchesclient.application.ports.out.ClientRepositoryPortOut;
import br.com.fiaplanchesclient.application.usecases.CreateClientUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateClientConfig {

    @Bean
    CreateClientUseCase cadastraClientePortaEntrada(ClientRepositoryPortOut clientRepositoryPortOut){
        return new CreateClientUseCase(clientRepositoryPortOut);
    }
}
