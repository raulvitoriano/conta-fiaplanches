package br.com.fiaplanchesclient.infra.config;

import br.com.fiaplanchesclient.application.ports.out.ClientRepositoryPortOut;
import br.com.fiaplanchesclient.application.usecases.FindClientUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FindClientConfig {

    @Bean
    public FindClientUseCase findClientUseCase(ClientRepositoryPortOut clientRepositoryPortOut) {
        return new FindClientUseCase(clientRepositoryPortOut);
    }
}
