package com.example.shopapp.features.experimentPayment.domain.model



//1. Domain Layer (The Core Abstractions)
//This layer will contain pure Kotlin/Java code, independent of Android or specific payment gateway SDKs.
//
//PaymentMethod Interface:
//
//This is the central abstraction for any payment method.
//It defines the contract for how a payment method should behave.

sealed class PaymentMethodType {
    object CreditCard : PaymentMethodType()
    object PayPal : PaymentMethodType()
    object GooglePay : PaymentMethodType()
    data class Custom(val name: String) : PaymentMethodType() // For custom methods
}

data class PaymentDetails(
    val amount: Double,
    val currency: String,
    val description: String,
    val metadata: Map<String, Any> = emptyMap()
)

// Represents the outcome of a payment attempt
sealed class PaymentResult {
    data class Success(val transactionId: String, val message: String? = null) : PaymentResult()
    data class Failure(val errorCode: String, val message: String? = null, val details: String? = null) : PaymentResult()
    object Cancelled : PaymentResult()
    object Processing : PaymentResult()
}