package br.com.fiaplanchesclient.bdd;

import br.com.fiaplanchesclient.application.dtos.ClientRequestDto;
import br.com.fiaplanchesclient.infra.dto.ClientDto;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.E;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.kafka.clients.ClientRequest;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.lang.management.MemoryType;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class StepDefinition {

    private Response response;

    private ClientRequestDto clientDto;

    private String nomeAtual;

    private final String END_API_CLIENTE = "http://localhost:8085/v1/client";

    @Dado("o cliente de CPF {word} e nome {word}")
    public void oClienteDeCPFENomeDaniel(String cpf, String name) {
        clientDto = new ClientRequestDto(cpf, name);
    }
    @Quando("for realizada a chamada no endpoint de criação de cliente")
    public void for_realizada_a_chamada_no_endpoint_de_criação_de_cliente() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(clientDto)
                .when()
                .post(END_API_CLIENTE + "/create");

    }

    @Quando("for realizada a chamada no endpoint de delecao de cliente")
    public void for_realizada_a_chamada_no_endpoint_de_delecao_de_cliente() {

        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(END_API_CLIENTE + "/delete/" + clientDto.cpf());

        response.then().assertThat().statusCode(HttpStatus.OK.value());

    }

    @Quando("for realizada a chamada no endpoint de busca de cliente")
    public void forRealizadaAChamadaNoEndpointDeBuscaDeCliente() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(END_API_CLIENTE + "/find/" + clientDto.cpf());
    }

    @Quando("for realizada a chamada no endpoint de atualizacao de cliente")
    public void forRealizadaAChamadaNoEndpointDeAtualizacaoDeCliente() {
        clientDto = new ClientRequestDto(clientDto.cpf(), nomeAtual);

        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(clientDto)
                .when()
                .put(END_API_CLIENTE + "/update");
    }
    @Entao("o cliente deve ser criado com sucesso na base")
    public void o_cliente_deve_ser_criado_com_sucesso_na_base() {
            response.then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body(matchesJsonSchemaInClasspath("schemas/ClientSchema.json"));

    }

    @Entao("o cliente deve ser localizado com sucesso na base")
    public void oClienteDeveSerLocalizadoComSucessoNaBase() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("schemas/ClientSchema.json"));

    }

    @Entao("o cliente deve ser atualizado com sucesso na base")
    public void oClienteDeveSerAtualizadoComSucessoNaBase() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("schemas/ClientSchema.json"));

        response.then().assertThat().body("nome", Is.is(nomeAtual));
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
