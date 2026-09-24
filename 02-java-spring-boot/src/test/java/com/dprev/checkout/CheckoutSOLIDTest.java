package com.dprev.checkout.test;

import com.dprev.checkout.service.CheckoutService;
import com.dprev.checkout.domain.strategy.PixPagamento;
import com.dprev.checkout.domain.strategy.CartaoCreditoPagamento;
import com.dprev.checkout.domain.cascade.CartaoCorporativoPremium;
import com.dprev.checkout.domain.strategy.BoletoPagamento;
import com.dprev.checkout.domain.strategy.ValeRefeicaoPagamento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CheckoutSOLIDTest {
    @Autowired private CheckoutService checkout;
    @Autowired private PixPagamento pix;
    @Autowired private CartaoCreditoPagamento cartaoCredito;
    
    @Test
    public void executarTestesSolid() {
        System.out.println("\n🧪 ========================================================");
        System.out.println("🧪 INICIANDO SUITE DE TESTES COMPLETA DE SOLID (SPRING BOOT)");
        System.out.println("🧪 ========================================================");

        // Instanciando os métodos de pagamento do laboratório
        PixPagamento pix = new PixPagamento();
        CartaoCreditoPagamento cartaoCredito = new CartaoCreditoPagamento();
        BoletoPagamento boleto = new BoletoPagamento();
        CartaoCorporativoPremium cartaoCorp = new CartaoCorporativoPremium();
        ValeRefeicaoPagamento vr = new ValeRefeicaoPagamento();

        // =====================================================================
        // CENÁRIO 1: Processamento Polimórfico Padrão
        // =====================================================================
        System.out.println("\n🔹 [CENÁRIO 1] Processamento Polimórfico Padrão...");
        try {
            checkout.finalizarPedido(100.0, pix, "fabiano@email.com");
            checkout.finalizarPedido(250.50, cartaoCredito, "fabiano@email.com");
            checkout.finalizarPedido(50.0, boleto, "fabiano@email.com");
            System.out.println("✅ Polimorfismo básico executado sem erros.");
        } catch (Exception e) {
            System.err.println("❌ Erro inesperado no cenário polimórfico: " + e.getMessage());
        }

        // =====================================================================
        // CENÁRIO 2: Extensões Complexas e Contratos do Cartão Premium
        // =====================================================================
        System.out.println("\n🔹 [CENÁRIO 2] Extensões Complexas e Contratos do Cartão Premium...");
        try {
            checkout.finalizarPedido(5000.0, cartaoCorp, "diretoria@dprev.com");
            
            cartaoCorp.generateLogAuditoria(5000.0);
            cartaoCorp.processarTransacaoInternacional("USD");
            cartaoCorp.estornar(5000.0);
            
            System.out.println("✅ Validação de recursos estendidos do Cartão Corporativo concluída.");
        } catch (Exception e) {
            System.err.println("❌ Falha ao processar cascading do Cartão Premium: " + e.getMessage());
        }

        // =====================================================================
        // CENÁRIO 3: Análise de Quebra do Liskov Substitution Principle (LSP)
        // =====================================================================
        System.out.println("\n🔹 [CENÁRIO 3] Análise de Quebra do Liskov Substitution Principle...");
        try {
            checkout.finalizarPedido(50.0, vr, "fabiano@email.com");
            System.out.println("✅ VR Valor Baixo: Funcionou conforme esperado.");
        } catch (Exception e) {
            System.err.println("❌ Falha inesperada com valor baixo no VR.");
        }

        try {
            checkout.finalizarPedido(350.0, vr, "fabiano@email.com");
            System.err.println("❌ FALHA: A esteira aceitou o VR acima de R$ 300 sem lançar erro.");
        } catch (IllegalArgumentException e) {
            LocalAssert.assertEquals("🚨🍔Limite de transacao VR excedido!", e.getMessage(), "Comportamento de exceção oculta do VR (LSP)");
        }
    }
}

class LocalAssert {
    public static void assertEquals(Object esperado, Object atual, String mensagem) {
        if (!esperado.equals(atual)) {
            throw new AssertionError("❌ FALHA: " + mensagem + " | Esperado: [" + esperado + "] mas obteve: [" + atual + "]");
        }
        System.out.println("✅ SUCESSO AUTOMATIZADO: " + mensagem);
    }
}