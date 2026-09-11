package com.tqi.checkout.service;
import com.tqi.checkout.notification.Notificador;
import com.tqi.checkout.domain.MetodoPagamento;
import com.tqi.checkout.repository.PagamentoRepository;

// [🟥S] - SINGLE RESPONSIBILITY PRINCIPLE: Sua única função/razão para mudar é orquestrar o fluxo do checkout.
public class CheckoutService {
    // [🟪D] - DEPENDENCY INVERSION PRINCIPLE: Corrigido! Agora depende inteiramente da Abstração (Interface Notificador).
    private final Notificador notificador;
    private final PagamentoRepository pagamentoRepository;

    public CheckoutService(Notificador notificador, PagamentoRepository pagamentoRepository) {
        this.notificador = notificador;
        this.pagamentoRepository = pagamentoRepository;
    }

    // [🟩O] - OPEN/CLOSED PRINCIPLE: Fechado para modificação interna. Aceita qualquer MetodoPagamento vindo de fora.
    public void finalizarPedido(double valor, MetodoPagamento formaDePagamento, String destinoNotificacao) {
        System.out.println("--- Iniciando Checkout ---");
        // [🟨L] - LISKOV SUBSTITUTION PRINCIPLE: O polimorfismo aqui é exercido. 
        // Se a classe filha quebrar a regra de ouro do LSP (como o VR faz acima), este método falha sem culpa.
        // 1. Processa o pagamento via Polimorfismo (LSP)
        formaDePagamento.processar(valor);
        // 2. SALVA NO BANCO (Desacoplado, usando a interface)
        this.pagamentoRepository.salvar(formaDePagamento, valor, destinoNotificacao, null);
        // 3. Notifica o cliente
        notificador.enviarComprovante(destinoNotificacao, valor);
        System.out.println("--- Checkout Finalizado --- \n");
    }
}