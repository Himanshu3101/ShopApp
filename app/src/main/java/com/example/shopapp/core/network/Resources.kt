package com.example.shopapp.core.network

sealed class Resources<T>(
    message: String? = null,
    data: T? = null
) {
    class Loading<T> : Resources<T>()
    class Success <T> (val data : T?): Resources<T>(data = data)
    class Error<T> (val message: String?) : Resources<T>(message = message)
}