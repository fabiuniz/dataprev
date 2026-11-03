package com.dprev.checkout.modules.pagamento.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

// Serve META-INF/resources/index.html em "/" e "/index.html",
// sem depender do módulo de conteúdo estático do Helidon.
@ApplicationScoped
@Path("/")
public class IndexResource {

    private static final String ARQUIVO = "META-INF/resources/index.html";

    @GET
    @Produces(MediaType.TEXT_HTML + "; charset=UTF-8")
    public Response raiz() {
        return html();
    }

    @GET
    @Path("index.html")
    @Produces(MediaType.TEXT_HTML + "; charset=UTF-8")
    public Response index() {
        return html();
    }

    private Response html() {
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(ARQUIVO)) {
            if (in == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .type(MediaType.TEXT_PLAIN).entity("index.html não encontrado no classpath").build();
            }
            return Response.ok(new String(in.readAllBytes(), StandardCharsets.UTF_8)).build();
        } catch (IOException e) {
            return Response.serverError().type(MediaType.TEXT_PLAIN).entity("Erro ao ler index.html").build();
        }
    }
}