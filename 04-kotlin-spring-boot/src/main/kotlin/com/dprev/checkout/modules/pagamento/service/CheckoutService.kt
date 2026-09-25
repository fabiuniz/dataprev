package com.dprev.checkout.modules.pagamento.service

import com.dprev.checkout.modules.pagamento.domain.MetodoPagamento
import org.springframework.stereotype.Service

// [🟥S] - SINGLE RESPONSIBILITY PRINCIPLE: Sua única razão para mudar é a orquestração do fluxo de checkout do pedido.
// COMPARAÇÃO COM JAVA: O construtor primário do Kotlin 'class CheckoutService(private val...)' elimina a necessidade
// de declarar campos privados e métodos construtores verbosos, injetando o 'Notificador' automaticamente via Spring.
@Service
public class CheckoutService (private val notificador: Notificador){
    //private private val notificador: Notificador
    //public fun CheckoutService( notificador : Notificador) {
    //    this.notificador = notificador;
    //}
    // [🟩O] - OPEN-CLOSED PRINCIPLE: O método está fechado para modificações na sua estrutura interna, 
    // mas totalmente aberto a extensões, aceitando qualquer nova estratégia de 'MetodoPagamento' de forma dinâmica.
    public fun finalizarPedido(valor: Double  , formaDePagamento : MetodoPagamento , destinoNotificacao: String ) {
        println("--- Iniciando Checkout ---")
        // [🟨L] - LISKOV SUBSTITUTION PRINCIPLE: O polimorfismo em sua essência. O service confia plenamente 
        // no contrato. Nenhuma subclasse ou implementação fornecida pode violar as expectativas de comportamento aqui.
        formaDePagamento.processar(valor)
        // [🟪D] - DEPENDENCY INVERSION PRINCIPLE: O serviço não conhece implementações concretas (como Email ou SMS).
        // Ele depende exclusivamente da abstração da interface 'Notificador'.
        notificador.enviarComprovante(destinoNotificacao, valor)
        println("--- Checkout Finalizado --- \n")
    }
}
