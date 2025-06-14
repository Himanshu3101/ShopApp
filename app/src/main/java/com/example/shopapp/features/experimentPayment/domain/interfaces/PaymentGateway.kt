package com.example.shopapp.features.experimentPayment.domain.interfaces

import com.example.shopapp.features.experimentPayment.domain.model.PaymentDetails
import com.example.shopapp.features.experimentPayment.domain.model.PaymentMethodType
import com.example.shopapp.features.experimentPayment.domain.model.PaymentResult

interface PaymentGateway {
    fun getType(): PaymentMethodType
    suspend fun processPayment(paymentDetails: PaymentDetails, paymentData: Any?): PaymentResult
    // Potentially other methods:
    // suspend fun retrieveTransactionStatus(transactionId: String): PaymentResult
    // suspend fun refundPayment(transactionId: String, amount: Double): PaymentResult
}