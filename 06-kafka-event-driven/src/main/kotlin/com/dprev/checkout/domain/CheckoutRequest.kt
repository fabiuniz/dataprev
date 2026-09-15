package com.dprev.checkout.domain

data class CheckoutRequest(
    val transactionId: String,
    val cpfCliente: String,
    val valorTotal: Double,
    val timestamp: Long = System.currentTimeMillis()
)