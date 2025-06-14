package com.example.shopapp.features.experimentPayment.data.gateway_impl

import com.example.shopapp.features.experimentPayment.domain.interfaces.PaymentGateway
import com.example.shopapp.features.experimentPayment.domain.model.PaymentDetails
import com.example.shopapp.features.experimentPayment.domain.model.PaymentMethodType
import com.example.shopapp.features.experimentPayment.domain.model.PaymentResult

class PayPalPaymentGateway(private val payPalService: PayPalApiService) : PaymentGateway {
    override fun getType(): PaymentMethodType = PaymentMethodType.PayPal

    override suspend fun processPayment(paymentDetails: PaymentDetails, paymentData: Any?): PaymentResult {
        // Assume paymentData is PayPal specific data
        // Call PayPal SDK/API
        return try {
            val payPalResponse = payPalService.createPayment(
                amount = paymentDetails.amount,
                currency = paymentDetails.currency,
                // ... other PayPal specific parameters from paymentData
            )
            PaymentResult.Success(payPalResponse.id, "PayPal payment completed")
        } catch (e: Exception) {
            PaymentResult.Failure("PAYPAL_ERROR", e.message ?: "Unknown PayPal error")
        }
    }
}