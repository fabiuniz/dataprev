package com.dprev.checkout;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.dprev.checkout.modules.pagamento.domain.Estornavel;
import com.dprev.checkout.modules.pagamento.domain.MetodoPagamento;
import com.dprev.checkout.modules.pagamento.domain.PagamentoInternacional;
import com.dprev.checkout.modules.pagamento.domain.cascade.CartaoCorporativoPremium;
import com.dprev.checkout.modules.pagamento.domain.strategy.BoletoPagamento;
import com.dprev.checkout.modules.pagamento.domain.strategy.CartaoCreditoPagamento;
import com.dprev.checkout.modules.pagamento.domain.strategy.PixPagamento;
import com.dprev.checkout.modules.pagamento.domain.strategy.ValeRefeicaoPagamento;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

// Teste unitário PURO (sem container, sem Docker): valida SOLID/LSP rapidamente.
class CheckoutSOLIDTest {

    @Test
    void polimorfismoOcpDip() {
        List<MetodoPagamento> metodos = List.of(
                new PixPagamento(), new CartaoCreditoPagamento(), new BoletoPagamento(),
                new CartaoCorporativoPremium(), new ValeRefeicaoPagamento(300));
        for (MetodoPagamento m : metodos) {
            assertDoesNotThrow(() -> m.processar(new BigDecimal("50.00")), m.codigo());
        }
    }

    @Test
    void cascataIspLsp() {
        CartaoCorporativoPremium premium = new CartaoCorporativoPremium();
        assertTrue(premium instanceof MetodoPagamento);       // via CartaoBase (LSP)
        assertTrue(premium instanceof PagamentoInternacional); // ISP
        assertTrue(premium instanceof Estornavel);             // cascata de interfaces
        assertDoesNotThrow(() -> premium.processarTransacaoInternacional("USD"));
        assertDoesNotThrow(() -> premium.estornar(new BigDecimal("5000")));
        assertDoesNotThrow(() -> premium.generateLogAuditoria(new BigDecimal("5000")));
    }

    @Test
    void vrQuebraLspAcimaDoLimite() {
        ValeRefeicaoPagamento vr = new ValeRefeicaoPagamento(300);
        assertDoesNotThrow(() -> vr.processar(new BigDecimal("50")));
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> vr.processar(new BigDecimal("350")));
        assertEquals("Limite de transacao VR excedido!", ex.getMessage());
    }
}
