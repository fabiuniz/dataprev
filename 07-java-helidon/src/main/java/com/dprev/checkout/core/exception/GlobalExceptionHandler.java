package com.dprev.checkout.core.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<RuntimeException> {

    private static final Logger LOG = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @Override
    public Response toResponse(RuntimeException e) {
        // Respeita o status original do JAX-RS (404, 405, 415...)
        if (e instanceof WebApplicationException wae) {
            return wae.getResponse();
        }

        int status = 500;
        if (e instanceof RequisicaoDuplicadaException) {
            status = 409;
        } else if (e instanceof IllegalArgumentException || e instanceof MetodoPagamentoInvalidoException) {
            status = 400;
        } else {
            LOG.log(Level.SEVERE, "Erro não tratado", e);
        }

        Map<String, String> corpo = new LinkedHashMap<>();
        corpo.put("erro", e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName());

        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(corpo)
                .build();
    }
}