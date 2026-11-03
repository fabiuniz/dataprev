package com.dprev.checkout.modules.pagamento.controller;

import com.dprev.checkout.modules.pagamento.domain.Pagamento;
import com.dprev.checkout.modules.pagamento.repository.PagamentoRepository;
import com.dprev.checkout.modules.pagamento.service.CheckoutService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

// Equivalente ao @RestController. Idempotência REST: header "Idempotency-Key".
// @RequestScoped faz do recurso um bean CDI (necessário com bean-discovery-mode="annotated").
@RequestScoped
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CheckoutResource {

    @Inject
    CheckoutService service;

    @Inject
    PagamentoRepository repository;

    @GET
    @Path("/status")
    public Map<String, String> status() {
        return Map.of("status", "Servidor online!", "runtime", "Helidon");
    }

    @POST
    @Path("/checkout")
    public Response checkout(@HeaderParam("Idempotency-Key") String chave, @Valid CheckoutRequest req) {
        Pagamento p = service.finalizarPedido(req.metodo(), req.valor(), req.email(), chave);
        return Response.ok(Map.of(
                "mensagem", "Checkout finalizado com sucesso via " + p.metodo() + "!",
                "id", p.id(),
                "metodo", p.metodo(),
                "chaveIdempotencia", p.chaveIdempotencia())).build();
    }

    @GET
    @Path("/pagamentos")
    public List<Pagamento> listar() {
        return repository.listarTodos();
    }

    @GET
    @Path("/pagamentos/{id}")
    public Response buscar(@PathParam("id") Long id) {
        return repository.buscarPorId(id)
                .map(p -> Response.ok(p).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND)
                        .entity(Map.of("erro", "Pagamento não encontrado: " + id)).build());
    }
}