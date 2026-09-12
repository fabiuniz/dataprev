//@file:JvmName("Application")
package com.tqi.checkout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Main {

    public static void main(String[] args) {
        // COMPARAÇÃO COM KOTLIN: No Java, o ponto de entrada da aplicação obrigatoriamente 
        // precisa estar envelopado dentro de um método 'public static void main' contido em uma classe.
        SpringApplication.run(Main.class, args);
    }
    @GetMapping("/status")
    public String status() {
        return "Servidor online!";
    }
}