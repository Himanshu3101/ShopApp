package com.example.shopapp.features.experimentPayment.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.features.experimentPayment.domain.model.PaymentDetails
import com.example.shopapp.features.experimentPayment.domain.model.PaymentMethodType
import com.example.shopapp.features.experimentPayment.domain.model.PaymentResult
import com.example.shopapp.features.experimentPayment.domain.repository.PaymentGatewayRepository
import com.example.shopapp.features.experimentPayment.domain.usecase.ProcessPaymentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch



//3. Presentation Layer (UI Integration - MVVM)
//This layer deals with Android-specific UI components and ViewModels.
//
//PaymentViewModel:
//
//Interacts with ProcessPaymentUseCase to trigger payment processing.
//Exposes LiveData or Flow to the UI for payment status and available methods.
@HiltViewModel
class PaymentViewModel(
    private val processPaymentUseCase: ProcessPaymentUseCase,
    private val paymentGatewayRepository: PaymentGatewayRepository // To get available methods
) : ViewModel() {

    private val _paymentResult = MutableLiveData<PaymentResult>()
    val paymentResult: LiveData<PaymentResult> = _paymentResult

    private val _availablePaymentMethods = MutableLiveData<List<PaymentMethodType>>()
    val availablePaymentMethods: LiveData<List<PaymentMethodType>> = _availablePaymentMethods

    init {
        _availablePaymentMethods.value = paymentGatewayRepository.getAllAvailablePaymentMethods()
    }

    fun initiatePayment(
        methodType: PaymentMethodType,
        amount: Double,
        currency: String,
        description: String,
        paymentData: Any? // UI-specific data, e.g., card number, PayPal token
    ) {
        _paymentResult.value = PaymentResult.Processing // Indicate loading state
        viewModelScope.launch {
            val details = PaymentDetails(amount, currency, description)
            val result = processPaymentUseCase(methodType, details, paymentData)
            _paymentResult.postValue(result)
        }
    }
}