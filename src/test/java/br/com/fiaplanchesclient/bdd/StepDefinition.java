package br.com.fiaplanchesclient.bdd;

import br.com.fiaplanchesclient.application.dtos.ClientRequestDto;
import br.com.fiaplanchesclient.infra.dto.ClientDto;
import br.com.fiaplanchesclient.infra.repository.PostGresClienteRepository;
import br.com.fiaplanchesclient.infra.repository.entity.ClientEntity;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.core.Is;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepDefinition {

    @Autowired
    private PostGresClienteRepository clienteRepository;

    private Response response;

    private ClientRequestDto clientDto;

    private String nomeAtual;

    private final String END_API_CLIENTE = "http://localhost:8085/v1/client";

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost:" + 8085;
        clienteRepository.deleteAll();

        System.out.println("Including Data.....");

        ClientEntity c1 = new ClientEntity( new ClientDto(1L,
                "38037984850",
                "Daniel Tavares"));
        ClientEntity c2 = new ClientEntity( new ClientDto(2L,
                "49174699881",
                "Nicole Tavares"));
        ClientEntity c3 = new ClientEntity( new ClientDto(3L,
                "10640145850",
                "Cristian Macedo"));
        ClientEntity c4 = new ClientEntity( new ClientDto(4L,
                "16494823882",
                "Juan Silva"));

        clienteRepository.saveAll(List.of(c1, c2, c3, c4));

        System.out.println("data Included.....");

        clientDto = new ClientRequestDto("", "");
    }

    @Dado("o cliente de CPF {word} e nome {string}")
    public void oClienteDeCPFENome(String cpf, String name) {
        clientDto = new ClientRequestDto(cpf, name);
    }

    @Quando("for realizada a chamada no endpoint de criação de cliente")
    public void for_realizada_a_chamada_no_endpoint_de_criação_de_cliente() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(clientDto)
                .when()
                .post(END_API_CLIENTE + "/create");

        response.then()
                .statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("schemas/ClientSchema.json"));

    }

    @Quando("for realizada a chamada no endpoint de busca de cliente")
    public void forRealizadaAChamadaNoEndpointDeBuscaDeCliente() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(END_API_CLIENTE + "/find/" + clientDto.cpf());

        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("schemas/ClientSchema.json"));
    }

    @Quando("for realizada a chamada no endpoint de atualizacao de cliente")
    public void forRealizadaAChamadaNoEndpointDeAtualizacaoDeCliente() {
        clientDto = new ClientRequestDto(clientDto.cpf(), nomeAtual);

        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(clientDto)
                .when()
                .put(END_API_CLIENTE + "/update");

        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("schemas/ClientSchema.json"));

        response.then().assertThat().body("nome", Is.is(nomeAtual));
    }

    @Quando("for realizada a chamada no endpoint de delecao de cliente")
    public void for_realizada_a_chamada_no_endpoint_de_delecao_de_cliente() {

        response = given()
                .when()
                .delete(END_API_CLIENTE + "/delete/" + clientDto.cpf());

        response.then().assertThat().statusCode(HttpStatus.OK.value());
        System.out.println(response.body().prettyPrint());

    }

    @Entao("o cliente deve ser localizado com sucesso na base")
    public void oClienteDeveSerLocalizadoComSucessoNaBase() {
        ClientEntity clienteEntity = clienteRepository.findByCpf(clientDto.cpf()).
                orElseThrow();

        assertEquals(clientDto.nome(), clienteEntity.toClienteDto().nome());
    }

    @Entao("o cliente deve ser atualizado com sucesso na base")
    public void oClienteDeveSerAtualizadoComSucessoNaBase() {

        ClientEntity clienteEntity = clienteRepository.findByCpf(clientDto.cpf()).
                orElseThrow();

        assertEquals(nomeAtual, clienteEntity.toClienteDto().nome());
    }

    @E("alterar os dados para CPF {word} e nome {word}")
    public void alterarOsDadosParaCPFENomeCleiton(String cpf, String name) {
        nomeAtual = name;
    }


    @Entao("o cliente não deve ser encontrado na base")
    public void oClienteNaoDeveSerEncontradoNaBase() {

        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(END_API_CLIENTE + "/find/" + clientDto.cpf());

        response.then().assertThat().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
