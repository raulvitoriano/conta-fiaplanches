package br.com.fiaplanchesclient.infra.config;

import br.com.fiaplanchesclient.application.ports.out.ClientRepositoryPortOut;
import br.com.fiaplanchesclient.application.usecases.DeleteClientUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeleteClientConfig {

    @Bean
    public DeleteClientUseCase deleteClientUseCase(ClientRepositoryPortOut clientRepositoryPortOut) {
        return new DeleteClientUseCase(clientRepositoryPortOut);
    }
}
