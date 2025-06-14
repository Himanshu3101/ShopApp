package com.example.shopapp.features.experimentPayment.domain.usecase

import com.example.shopapp.features.experimentPayment.domain.model.PaymentDetails
import com.example.shopapp.features.experimentPayment.domain.model.PaymentMethodType
import com.example.shopapp.features.experimentPayment.domain.model.PaymentResult
import com.example.shopapp.features.experimentPayment.domain.repository.PaymentGatewayRepository


//ProcessPaymentUseCase (Use Case/Interactor):
//
//Encapsulates the high-level business logic for processing a payment.
//It orchestrates the interaction between the PaymentGatewayRepository and the PaymentGateway itself.
class ProcessPaymentUseCase(
    private val paymentGatewayRepository: PaymentGatewayRepository
) {
    suspend operator fun invoke(
        paymentMethodType: PaymentMethodType,
        paymentDetails: PaymentDetails,
        paymentData: Any? // Specific data like card details, PayPal tokens, etc.
    ): PaymentResult {
        val gateway = paymentGatewayRepository.getPaymentGateway(paymentMethodType)
            ?: return PaymentResult.Failure("INVALID_METHOD", "Payment method not supported.")

        return gateway.processPayment(paymentDetails, paymentData)
    }
}