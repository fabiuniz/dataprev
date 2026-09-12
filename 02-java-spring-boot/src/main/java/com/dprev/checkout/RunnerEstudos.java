package com.dprev.checkout;

import com.dprev.checkout.domain.strategy.PixPagamento;
import com.dprev.checkout.domain.strategy.CartaoCreditoPagamento;
import com.dprev.checkout.domain.strategy.BoletoPagamento;
import com.dprev.checkout.domain.strategy.ValeRefeicaoPagamento;
import com.dprev.checkout.domain.cascade.CartaoCorporativoPremium;
import com.dprev.checkout.service.CheckoutService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
public class RunnerEstudos implements CommandLineRunner {

    private final CheckoutService checkout;
    private final PixPagamento pix;
    private final CartaoCreditoPagamento cartaoCredito;
    private final BoletoPagamento shadowBoleto; // Corrigido o nome para coincidir com o uso interno
    private final ValeRefeicaoPagamento vr;
    private final CartaoCorporativoPremium cartaoCorp; // Corrigido de cartaoCor para cartaoCorp

    // Injeção de dependência pelo construtor do Spring Boot
    public RunnerEstudos(CheckoutService checkout, PixPagamento pix, CartaoCreditoPagamento cartaoCredito,
                         BoletoPagamento shadowBoleto, ValeRefeicaoPagamento vr, CartaoCorporativoPremium cartaoCorp) {
        this.checkout = checkout;
        this.pix = pix;
        this.cartaoCredito = cartaoCredito;
        this.shadowBoleto = shadowBoleto;
        this.vr = vr;
        this.cartaoCorp = cartaoCorp;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n🧪 ========================================================");
        System.out.println("🧪 INICIANDO SUITE DE TESTES AUTOMATIZADOS (SPRING BOOT)");
        System.out.println("🧪 ========================================================\n");

        System.out.println("🔹 [CENÁRIO 1] Processamento Polimórfico Padrão...");
        checkoutService.finalizarPedido(100.0, pix, "fabiano@email.com");
        checkoutService.finalizarPedido(250.50, cartaoCredito, "fabiano@email.com");

        System.out.println("🔹 [CENÁRIO 2] Extensões Complexas e Contratos do Cartão Premium...");
        checkoutService.finalizarPedido(5000.0, cartaoCorp, "diretoria@dprev.com");
        cartaoCorp.generateLogAuditoria(5000.0);
        cartaoCorp.processarTransacaoInternacional("USD");
        cartaoCorp.estornar(5000.0);

        System.out.println("\n🔹 [CENÁRIO 3] Análise de Quebra do Liskov Substitution Principle (LSP)...");
        checkoutService.finalizarPedido(50.0, vr, "fabiano@email.com");

        try {
            checkoutService.finalizarPedido(350.0, vr, "fabiano@email.com");
        } catch (IllegalArgumentException e) {
            System.out.println("[ALERTA ERRO] LSP Quebrado sob análise: " + e.getMessage());
            System.out.println("✅ SUCESSO AUTOMATIZADO: O Spring tratou a exceção do polimorfismo restrito.");
        }
        
        System.out.println("\n🚀 ========================================================");
        System.out.println("🚀 TESTES CONCLUÍDOS. O SERVIDOR CONTINUA ATIVO NA PORTA 8080.");
        System.out.println("🚀 ========================================================\n");
    }
}