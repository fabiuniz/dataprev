package com.dprev.checkout.core.exception;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Map;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

// Equivalente ao @RestControllerAdvice do Spring.
public class GlobalExceptionHandler {

    @ServerExceptionMapper
    public Response metodoInvalido(MetodoPagamentoInvalidoException e) {
        return erro(400, e.getMessage());
    }

    // Regras de negócio violadas (ex.: limite do VR excedido - quebra do LSP)
    @ServerExceptionMapper
    public Response regraNegocio(IllegalArgumentException e) {
        return erro(400, e.getMessage());
    }

    // Idempotência
    @ServerExceptionMapper
    public Response duplicada(RequisicaoDuplicadaException e) {
        return erro(409, e.getMessage());
    }

    private static Response erro(int status, String mensagem) {
        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(Map.of("erro", mensagem))
                .build();
    }
}
