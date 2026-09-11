package com.tqi.checkout.domain.pix;
import com.tqi.checkout.domain.MetodoPagamento;

// [🟨L] - LISKOV SUBSTITUTION PRINCIPLE: Substitui a interface mãe perfeitamente sem quebras de comportamento.
public class PixPagamento implements MetodoPagamento {
    @Override
    public void processar(double valor) {
        System.out.println("⚡PIX: Gerando QR Code no valor de R$ " + valor);
    }
}
