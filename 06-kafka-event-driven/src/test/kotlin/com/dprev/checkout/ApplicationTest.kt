package com.dprev.checkout

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [Application::class])
class ApplicationTest {

    @Test
    fun contextLoads() {
        // Valida se o contexto do Spring Boot e o ecossistema Kafka sobem perfeitamente
    }
}