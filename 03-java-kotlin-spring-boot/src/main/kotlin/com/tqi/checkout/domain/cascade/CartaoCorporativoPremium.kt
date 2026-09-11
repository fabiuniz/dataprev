package com.tqi.checkout.domain.cascade

import com.tqi.checkout.domain.base.CartaoBase
import com.tqi.checkout.domain.PagamentoInternacional
import org.springframework.stereotype.Component

// [🟩O] - OPEN-CLOSED PRINCIPLE: Nova funcionalidade (cartão premium internacional) adicionada via extensão, sem modificar o código existente.
// [🟨L] - LISKOV SUBSTITUTION PRINCIPLE: Como estende 'CartaoBase', pode substituir perfeitamente qualquer referência a ele ou a 'MetodoPagamento'.
// [🟦I] - INTERFACE SEGREGATION PRINCIPLE: Implementa 'PagamentoInternacional' isoladamente, garantindo que métodos específicos (como estorno/conversão) não poluam a interface base.
// COMPARAÇÃO COM JAVA: Usa ':' tanto para herança quanto para interfaces. O Spring '@Component' gerencia o ciclo de vida de forma idêntica.
@Component
public class CartaoCorporativoPremium : CartaoBase(), PagamentoInternacional {
    public override fun processarTransacaoInternacional(moedaEstrangeira: String) {
        println("Corporativo Premium: Convertendo taxa para $moedaEstrangeira")
    }
    public override fun estornar(valor: Double) {
        println("Corporativo Premium: Estornando teto de R$ $valor com prioridade.")
    }
}
