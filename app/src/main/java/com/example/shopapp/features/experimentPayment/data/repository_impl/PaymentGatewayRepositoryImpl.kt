package com.example.shopapp.features.experimentPayment.data.repository_impl

import com.example.shopapp.features.experimentPayment.domain.interfaces.PaymentGateway
import com.example.shopapp.features.experimentPayment.domain.model.PaymentMethodType
import com.example.shopapp.features.experimentPayment.domain.repository.PaymentGatewayRepository

//PaymentGatewayRepositoryImpl:
//
//Implements the PaymentGatewayRepository interface.
//Manages a collection of concrete PaymentGateway instances.


class PaymentGatewayRepositoryImpl(
    private val paymentGateways: List<PaymentGateway> // Injected
) : PaymentGatewayRepository {

    private val gatewayMap = paymentGateways.associateBy {
        it.getType()
    }

    override fun getPaymentGateway(type: PaymentMethodType): PaymentGateway? {
        return gatewayMap[type]
    }

    override fun getAllAvailablePaymentMethods(): List<PaymentMethodType> {
        return paymentGateways.map { it.getType() }
    }
}