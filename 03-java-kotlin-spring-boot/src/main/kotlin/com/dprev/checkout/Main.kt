@file:JvmName("Application")

package com.dprev.checkout

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
public class Main

public fun main(args: Array<String>) {
    // COMPARAÇÃO COM JAVA: Funções de nível superior (Top-level functions) eliminam a necessidade 
    // de aninhar o ponto de entrada principal dentro de blocos de classes estáticas redundantes.
    runApplication<Main>(*args)
}