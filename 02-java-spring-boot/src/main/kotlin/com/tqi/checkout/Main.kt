package com.tqi.checkout
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import com.tqi.checkout.service.CheckoutService
import com.tqi.checkout.domain.strategy.PixPagamento

@SpringBootApplication
class Main {
    @Bean fun run(s: CheckoutService, p: PixPagamento) = CommandLineRunner { s.finalizarPedido(400.0, p) }
}
fun main(args: Array<String>) { runApplication<Main>(*args) }
