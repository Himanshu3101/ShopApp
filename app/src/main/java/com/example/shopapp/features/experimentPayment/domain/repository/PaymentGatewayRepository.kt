package com.example.shopapp.features.experimentPayment.domain.repository

import com.example.shopapp.features.experimentPayment.domain.interfaces.PaymentGateway
import com.example.shopapp.features.experimentPayment.domain.model.PaymentMethodType


//PaymentGatewayRepository Interface:
//
//Defines the contract for interacting with all available payment gateways.
//This interface abstracts away the source of payment gateway implementations.
interface PaymentGatewayRepository {
    fun getPaymentGateway(type: PaymentMethodType): PaymentGateway?
    fun getAllAvailablePaymentMethods(): List<PaymentMethodType>
}