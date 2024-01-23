package br.com.fiaplanchesclient.infra.config;

import br.com.fiaplanchesclient.application.ports.out.ClientRepositoryPortOut;
import br.com.fiaplanchesclient.application.usecases.UpdateClientUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateClientConfig {

    @Bean
    public UpdateClientUseCase updateClientUseCase(ClientRepositoryPortOut clientRepositoryPortOut) {
        return new UpdateClientUseCase(clientRepositoryPortOut);
    }
}
