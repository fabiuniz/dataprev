package com.dprev.checkout;

import com.dprev.checkout.domain.cascade.CartaoCorporativoPremium;
import com.dprev.checkout.domain.strategy.CartaoCreditoPagamento;
import com.dprev.checkout.domain.strategy.PixPagamento;
import com.dprev.checkout.domain.strategy.ValeRefeicaoPagamento;
import com.dprev.checkout.service.CheckoutService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RunnerEstudos implements CommandLineRunner {

    private final CheckoutService checkoutService;
    private final PixPagamento pix;
    private final CartaoCreditoPagamento cartaoCredito;
    private final CartaoCorporativoPremium cartaoCorp;
    private final ValeRefeicaoPagamento vr;

    public RunnerEstudos(CheckoutService checkoutService,
                         PixPagamento pix,
                         CartaoCreditoPagamento cartaoCredito,
                         CartaoCorporativoPremium cartaoCorp,
                         ValeRefeicaoPagamento vr) {
        this.checkoutService = checkoutService;
        this.pix = pix;
        this.cartaoCredito = cartaoCredito;
        this.cartaoCorp = cartaoCorp;
        this.vr = vr;
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