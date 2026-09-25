package com.dprev.checkout.modules.pagamento.domain.strategy;
import com.dprev.checkout.modules.pagamento.domain.MetodoPagamento;

// [🟩O] - Demonstração do OCP: Classe plugável adicionada ao ecossistema sem alterar os serviços centrais.
public class BoletoPagamento implements MetodoPagamento {
    @Override
    public void processar(double valor) {
        System.out.println("📄Boleto: Gerando linha digitável para o valor de R$ " + valor);
    }
}
