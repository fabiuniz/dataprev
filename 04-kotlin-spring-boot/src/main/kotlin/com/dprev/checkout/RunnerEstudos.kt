package com.dprev.checkout

import com.dprev.checkout.domain.strategy.PixPagamento
import com.dprev.checkout.domain.strategy.CartaoCreditoPagamento
import com.dprev.checkout.domain.strategy.BoletoPagamento
import com.dprev.checkout.domain.strategy.ValeRefeicaoPagamento
import com.dprev.checkout.domain.cascade.CartaoCorporativoPremium
import com.dprev.checkout.service.CheckoutService
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
public class RunnerEstudos (
    private var checkout : CheckoutService,
    private var pix: PixPagamento,
    private var cartaoCredito: CartaoCreditoPagamento,
    private var shadowBoleto: BoletoPagamento,
    private var vr: ValeRefeicaoPagamento,
    private var cartaoCorp: CartaoCorporativoPremium
): CommandLineRunner {

    // Injeção de dependência pelo construtor do Spring Boot
    public fun RunnerEstudos(checkout: CheckoutService , pix: PixPagamento , cartaoCredito: CartaoCreditoPagamento ,
                             shadowBoleto: BoletoPagamento , vr :ValeRefeicaoPagamento , cartaoCorp: CartaoCorporativoPremium ) {
        this.checkout = checkout;
        this.pix = pix;
        this.cartaoCredito = cartaoCredito;
        this.shadowBoleto = shadowBoleto;
        this.vr = vr;
        this.cartaoCorp = cartaoCorp;
    }

    @Override
    public override fun run(vararg args: String) {
        println("\n=== TESTANDO ESTRUTURA ORIGINAL VIA SPRING ===");
        // [🟩O] e [🟨L] - POLIMORFISMO: O checkout consome as estratégias estavelmente
        // via contrato 'MetodoPagamento', sem necessidade de conhecer suas especificidades.
        checkout.finalizarPedido(100.0, pix, "fabiano@email.com");
        checkout.finalizarPedido(250.50, cartaoCredito, "fabiano@email.com");
        checkout.finalizarPedido(50.0, shadowBoleto, "fabiano@email.com");

        println("=== TESTANDO CONCEITOS DE CASCATA (EXTENDS & IMPLEMENTS) ===");
        checkout.finalizarPedido(5000.0, cartaoCorp, "diretoria@dprev.com");

        println("Verificando rastreabilidade do cartão corporativo:");
        cartaoCorp.generateLogAuditoria(5000.0);
        cartaoCorp.processarTransacaoInternacional("USD");
        cartaoCorp.estornar(5000.0);

        System.out.println("\n=== TESTANDO VIOLAÇÃO DO LSP (ESTUDO DE CASO) ===");
        checkout.finalizarPedido(50.0, vr, "fabiano@email.com")

        // [🟨L] - LISKOV SUBSTITUTION PRINCIPLE (VIOLAÇÃO): O código cliente espera comportamento uniforme.
        // Lançar uma exceção de validação oculta em uma subclasse quebra as expectativas do polimorfismo puro.
        try {
            checkout.finalizarPedido(350.0, vr, "fabiano@email.com")
        } catch (e:IllegalArgumentException ) {
            // CORRIGIDO: Sintaxe de interpolação do Kotlin trocada pela concatenação do Java e uso de getMessage()
            println("[ALERTA ERRO] LSP Quebrado sob análise: ${e.message}")
            println("O código cliente não deve ser forçado a prever regras restritivas exclusivas de um subtipo.")
        }
    }
}