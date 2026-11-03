package com.dprev.checkout;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.inject.Inject;
import java.util.UUID;
import org.junit.jupiter.api.Test;

// Integração: Postgres e Redis sobem sozinhos via Dev Services (precisa de Docker ativo).
@QuarkusTest
class CheckoutResourceTest {

    @Inject NotificadorFake notificador;

    private Response post(String metodo, double valor, String chave) {
        var req = given().contentType(ContentType.JSON)
                .body("{\"email\":\"fabiano@email.com\",\"valor\":" + valor
                        + ",\"metodo\":\"" + metodo + "\"}");
        if (chave != null) {
            req = req.header("Idempotency-Key", chave);
        }
        return req.post("/api/checkout");
    }

    @Test
    void pixComSucesso() {
        post("PIX", 100.0, UUID.randomUUID().toString())
                .then().statusCode(200).body("metodo", is("PIX"));
        assertTrue(notificador.enviados.stream().anyMatch(s -> s.startsWith("fabiano@email.com")));
    }

    @Test
    void mesmaChaveRetorna409() {
        String chave = UUID.randomUUID().toString();
        post("CARTAO", 250.50, chave).then().statusCode(200);
        post("CARTAO", 250.50, chave).then().statusCode(409);
    }

    @Test
    void chavesDiferentesNaoColidem() {
        post("BOLETO", 50.0, UUID.randomUUID().toString()).then().statusCode(200);
        post("BOLETO", 50.0, UUID.randomUUID().toString()).then().statusCode(200);
    }

    @Test
    void semHeaderGeraChaveAutomatica() {
        post("PIX", 10.0, null).then().statusCode(200);
        post("PIX", 10.0, null).then().statusCode(200);
    }

    @Test
    void vrAcimaDoLimiteRetorna400ENaoTravaChave() {
        String chave = UUID.randomUUID().toString();
        post("VR", 350.0, chave).then().statusCode(400).body("erro", containsString("Limite"));
        // a mesma chave continua livre para uma tentativa válida
        post("VR", 50.0, chave).then().statusCode(200);
    }

    @Test
    void metodoInvalidoRetorna400() {
        post("BITCOIN", 10.0, UUID.randomUUID().toString()).then().statusCode(400);
    }

    @Test
    void emailInvalidoRetorna400() {
        given().contentType(ContentType.JSON)
                .body("{\"email\":\"nao-e-email\",\"valor\":10,\"metodo\":\"PIX\"}")
                .post("/api/checkout").then().statusCode(400);
    }

    @Test
    void corporativoPremiumFuncionaViaCascata() {
        post("CORPORATIVO", 5000.0, UUID.randomUUID().toString()).then().statusCode(200);
    }

    @Test
    void listagemRetornaPagamentos() {
        post("PIX", 1.0, UUID.randomUUID().toString()).then().statusCode(200);
        given().get("/api/pagamentos").then().statusCode(200)
                .body("size()", greaterThanOrEqualTo(1));
    }

    @Test
    void pagamentoInexistenteRetorna404() {
        given().get("/api/pagamentos/999999").then().statusCode(404);
    }
}
