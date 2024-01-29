package br.com.fiaplanchesclient;

import br.com.fiaplanchesclient.domain.Client;
import br.com.fiaplanchesclient.infra.dto.ClientDto;
import br.com.fiaplanchesclient.infra.repository.PostGresClienteRepository;
import br.com.fiaplanchesclient.infra.repository.entity.ClientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class FiapLanchesClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(FiapLanchesClientApplication.class, args);
    }

    @Autowired
    PostGresClienteRepository clienteRepository;

    // Run this if app.db.init.enabled = true
    @Bean
    @ConditionalOnProperty(prefix = "app", name = "db.init.enabled", havingValue = "true")
    public CommandLineRunner demoCommandLineRunner() {
        return args -> {

            System.out.println("Running.....");

            ClientEntity c1 = new ClientEntity( new ClientDto(1L,
                    "38037984850",
                   "Joao"));
            ClientEntity c2 = new ClientEntity( new ClientDto(2L,
                    "49174699881",
                    "Cleiton"));
            ClientEntity c3 = new ClientEntity( new ClientDto(3L,
                    "12345678910",
                    "Gi"));
            ClientEntity c4 = new ClientEntity( new ClientDto(4L,
                    "10987654321",
                    "Camargo"));

            clienteRepository.saveAll(List.of(c1, c2, c3, c4));

            System.out.println("Database updated.....");

        };
    }

}
