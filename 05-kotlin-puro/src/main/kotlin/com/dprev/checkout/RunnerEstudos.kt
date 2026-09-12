package com.dprev.checkout

import com.dprev.checkout.domain.strategy.*
import com.dprev.checkout.domain.cascade.CartaoCorporativoPremium
import com.dprev.checkout.service.CheckoutService

// Removemos o //@Component e a interface CommandLineRunner
public class RunnerEstudos(
    // COMPARAÇÃO COM JAVA: Primary Constructor. As dependências são declaradas diretamente na assinatura.
    // O Kotlin injeta e cria os campos privados em uma única linha, eliminando o boilerplate do construtor Java.
    // KOTLIN REVOLUTION: Primary Constructor. As dependências são declaradas diretamente
    // na assinatura da classe. O Kotlin injeta e cria os campos privados em uma única linha,
    // eliminando dezenas de linhas de construtores repetitivos do Java.
    private val checkout: CheckoutService,
    private val pix: PixPagamento,
    private val cartaoCredito: CartaoCreditoPagamento,
    private val shadowBoleto: BoletoPagamento,
    private val vr: ValeRefeicaoPagamento,
    private val cartaoCorp: CartaoCorporativoPremium
) {

    // Alterado de 'run(vararg args...)' para um método comum
    public fun executar() {
        // [🟩O] e [🟨L] - POLIMORFISMO: O checkout consome as estratégias estavelmente 
        // via contrato 'MetodoPagamento', sem necessidade de conhecer suas especificidades.
        checkout.finalizarPedido(100.0, pix, "fabiano@email.com")
        checkout.finalizarPedido(250.50, cartaoCredito, "fabiano@email.com")
        checkout.finalizarPedido(50.0, shadowBoleto, "fabiano@email.com")

        println("=== TESTANDO CONCEITOS DE CASCATA (EXTENDS & IMPLEMENTS) ===")
        checkout.finalizarPedido(5000.0, cartaoCorp, "diretoria@dprev.com")

        cartaoCorp.generateLogAuditoria(5000.0)
        cartaoCorp.processarTransacaoInternacional("USD")
        cartaoCorp.estornar(5000.0)

        println("\n=== TESTANDO VIOLAÇÃO DO LSP (ESTUDO DE CASO) ===")
        checkout.finalizarPedido(50.0, vr, "fabiano@email.com")
        
        // [🟨L] - LISKOV SUBSTITUTION PRINCIPLE (VIOLAÇÃO): O código cliente espera comportamento uniforme.
        // Lançar uma exceção de validação oculta em uma subclasse quebra as expectativas do polimorfismo puro.
        try {
            checkout.finalizarPedido(350.0, vr, "fabiano@email.com")
        } catch (e: IllegalArgumentException) {
            println("[ALERTA ERRO] LSP Quebrado sob análise: ${e.message}")
        }
    }
}