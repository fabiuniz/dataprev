package com.dprev.checkout.controller;

import com.dprev.checkout.domain.MetodoPagamento;
import com.dprev.checkout.domain.strategy.PixPagamento;
import com.dprev.checkout.domain.strategy.CartaoCreditoPagamento;
import com.dprev.checkout.domain.strategy.BoletoPagamento;
import com.dprev.checkout.domain.strategy.ValeRefeicaoPagamento;
import com.dprev.checkout.service.CheckoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CheckoutController {

    private final CheckoutService checkoutService;
    private final PixPagamento pix;
    private final CartaoCreditoPagamento cartaoCredito;
    private final BoletoPagamento boleto;
    private final ValeRefeicaoPagamento vr;

    public CheckoutController(CheckoutService checkoutService,
                              PixPagamento pix,
                              CartaoCreditoPagamento cartaoCredito,
                              BoletoPagamento boleto,
                              ValeRefeicaoPagamento vr) {
        this.checkoutService = checkoutService;
        this.pix = pix;
        this.cartaoCredito = cartaoCredito;
        this.boleto = boleto;
        this.vr = vr;
    }

    @PostMapping("/checkout")
    public ResponseEntity<String> finalizarPedido(@RequestBody CheckoutRequest request) {
        MetodoPagamento estrategia = switch (request.getMetodo().toUpperCase().trim()) {
            case "PIX" -> pix;
            case "CARTAO", "CREDITO" -> cartaoCredito;
            case "BOLETO" -> boleto;
            case "VR", "VALE_REFEICAO" -> vr;
            default -> throw new IllegalArgumentException("Método de pagamento inválido: " + request.getMetodo());
        };

        checkoutService.finalizarPedido(request.getValor(), estrategia, request.getEmail());
        return ResponseEntity.ok("Checkout finalizado com sucesso via " + request.getMetodo().toUpperCase() + "!");
    }

    // DTO interno para mapear o JSON vindo do front-end
    public static class CheckoutRequest {
        private String email;
        private double valor;
        private String metodo;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public double getValor() { return valor; }
        public void setValor(double valor) { this.valor = valor; }

        public String getMetodo() { return metodo; }
        public void setMetodo(String metodo) { this.metodo = metodo; }
    }
}