package com.dprev.checkout.service;

import com.dprev.checkout.notification.Notificador;
import com.dprev.checkout.domain.MetodoPagamento;
import com.dprev.checkout.repository.PagamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

// [🟥S] - SINGLE RESPONSIBILITY PRINCIPLE: Sua única razão para mudar é a orquestração do fluxo de checkout do pedido.
// COMPARAÇÃO COM JAVA: O construtor primário do Kotlin 'class CheckoutService(private val...)' elimina a necessidade
// de declarar campos privados e métodos construtores verbosos, injetando o 'Notificador' automaticamente via Spring.
@Service
public class CheckoutService {
    private final Notificador notificador;
    private final PagamentoRepository pagamentoRepository;
    public CheckoutService(Notificador notificador, PagamentoRepository pagamentoRepository) {
        this.notificador = notificador; 
        this.pagamentoRepository = pagamentoRepository;
    }
    // [🟩O] - OPEN-CLOSED PRINCIPLE: O método está fechado para modificações na sua estrutura interna, 
    // mas totalmente aberto a extensões, aceitando qualquer nova estratégia de 'MetodoPagamento' de forma dinâmica.
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) 
    public void finalizarPedido(double valor , MetodoPagamento formaDePagamento , String destinoNotificacao ) {
        System.out.println("--- Iniciando Checkout ---");
        // [🟨L] - LISKOV SUBSTITUTION PRINCIPLE: O polimorfismo em sua essência. O service confia plenamente 
        // no contrato. Nenhuma subclasse ou implementação fornecida pode violar as expectativas de comportamento aqui.
        pagamentoRepository.salvar(formaDePagamento, valor, destinoNotificacao, null);
        formaDePagamento.processar(valor);
        // [🟪D] - DEPENDENCY INVERSION PRINCIPLE: O serviço não conhece implementações concretas (como Email ou SMS).
        // Ele depende exclusivamente da abstração da interface 'Notificador'.
        notificador.enviarComprovante(destinoNotificacao, valor);
        System.out.println("--- Checkout Finalizado --- \n");
    }
}
