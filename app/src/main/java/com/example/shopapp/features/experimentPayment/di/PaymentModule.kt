package com.example.shopapp.features.experimentPayment.di

import com.example.shopapp.features.experimentPayment.data.gateway_impl.PayPalPaymentGateway
import com.example.shopapp.features.experimentPayment.data.repository_impl.PaymentGatewayRepositoryImpl
import com.example.shopapp.features.experimentPayment.data.gateway_impl.StripePaymentGateway
import com.example.shopapp.features.experimentPayment.domain.interfaces.PaymentGateway
import com.example.shopapp.features.experimentPayment.domain.repository.PaymentGatewayRepository
import com.example.shopapp.features.experimentPayment.domain.usecase.ProcessPaymentUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//4. Dependency Injection (e.g., Hilt)
//This is crucial for assembling the pieces and adhering to DIP.



@Module
@InstallIn(SingletonComponent::class)
object PaymentModule {

    @Provides
    @Singleton
    fun providePaymentGateways(
        stripeGateway: StripePaymentGateway,
        payPalGateway: PayPalPaymentGateway
    ): List<PaymentGateway> {
        return listOf(stripeGateway, payPalGateway)
    }

    @Provides
    @Singleton
    fun providePaymentGatewayRepository(
        paymentGateways: List<PaymentGateway>
    ): PaymentGatewayRepository {
        return PaymentGatewayRepositoryImpl(paymentGateways)
    }

    @Provides
    @Singleton
    fun provideProcessPaymentUseCase(
        repository: PaymentGatewayRepository
    ): ProcessPaymentUseCase {
        return ProcessPaymentUseCase(repository)
    }





    @Provides
    @Singleton
    fun provideStripeApiService(): StripeApiService {
        // Configure and return Stripe API service
        return StripeApiService() // Dummy for example
    }

    @Provides
    @Singleton
    fun providePayPalApiService(): PayPalApiService {
        // Configure and return PayPal API service
        return PayPalApiService() // Dummy for example
    }

    @Provides
    @Singleton
    fun provideStripePaymentGateway(stripeApiService: StripeApiService): StripePaymentGateway {
        return StripePaymentGateway(stripeApiService)
    }

    @Provides
    @Singleton
    fun providePayPalPaymentGateway(payPalApiService: PayPalApiService): PayPalPaymentGateway {
        return PayPalPaymentGateway(payPalApiService)
    }
}