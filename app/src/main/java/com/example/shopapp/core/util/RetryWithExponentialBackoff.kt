package com.example.shopapp.core.util

import android.util.Log
import com.example.shopapp.core.util.Constants.Slog
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.retryWhen
import kotlin.math.pow

fun <T> Flow<T>.retryWithExponentialBackoff(
    maxRetries: Int = 3,
    initialDelayMillis: Long = 1000,
): Flow<T> = retryWhen { cause, attempt ->
    if (attempt < maxRetries) {
        val delayTime = initialDelayMillis * 2.0.pow(attempt.toDouble()).toLong()
        delay(delayTime)
        Log.e(Slog, delayTime.toString() )
        true
    } else {
        false
    }
}
