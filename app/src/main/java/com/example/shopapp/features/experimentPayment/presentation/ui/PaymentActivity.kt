package com.example.shopapp.features.experimentPayment.presentation.ui

import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.shopapp.features.experimentPayment.domain.model.PaymentMethodType
import com.example.shopapp.features.experimentPayment.domain.model.PaymentResult
import com.example.shopapp.features.experimentPayment.presentation.viewmodel.PaymentViewModel


//PaymentActivity / PaymentFragment (UI):
//
//Observes PaymentViewModel for updates.
//Displays available payment methods.
//Collects user input (card details, PayPal login).
//Calls initiatePayment() on the ViewModel.


class PaymentActivity : AppCompatActivity() {

    private val viewModel: PaymentViewModel by viewModels()
    // ... views for payment methods, amount, etc.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        // Observe available payment methods
        viewModel.availablePaymentMethods.observe(this) { methods ->
            // Update UI to show radio buttons or a spinner for payment methods
        }

        // Observe payment result
        viewModel.paymentResult.observe(this) { result ->
            when (result) {
                is PaymentResult.Success -> showSuccessMessage(result.message)
                is PaymentResult.Failure -> showErrorMessage(result.message)
                is PaymentResult.Cancelled -> showCancelledMessage()
                is PaymentResult.Processing -> showLoadingIndicator()
            }
        }

        // Example: Button click to initiate payment
        findViewById<Button>(R.id.payButton).setOnClickListener {
            val selectedMethod = getSelectedPaymentMethodFromUI() // e.g., from radio buttons
            val amount = getAmountFromUI()
            val paymentData = getPaymentDataFromUI(selectedMethod) // e.g., card details
            viewModel.initiatePayment(selectedMethod, amount, "USD", "Order #123", paymentData)
        }
    }

    // Helper functions to get data from UI
    private fun getSelectedPaymentMethodFromUI(): PaymentMethodType { /* ... */ }
    private fun getAmountFromUI(): Double { /* ... */ }
    private fun getPaymentDataFromUI(method: PaymentMethodType): Any? { /* ... */ }
    // ... functions to show messages, loading, etc.
}