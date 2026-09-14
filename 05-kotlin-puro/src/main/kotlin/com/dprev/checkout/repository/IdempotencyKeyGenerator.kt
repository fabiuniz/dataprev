package com.dprev.checkout.repository

import java.util.UUID

public class IdempotencyKeyGenerator {
    public fun gerarChave(email: String, valor: Double): String {
        return UUID.nameUUIDFromBytes("$email-$valor".toByteArray()).toString()
    }
}
