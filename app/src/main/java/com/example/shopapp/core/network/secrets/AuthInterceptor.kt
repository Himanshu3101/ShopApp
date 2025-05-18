package com.example.shopapp.core.network.secrets

import com.example.shopapp.core.network.secrets.KeysProvider.KeysProvider
import jakarta.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor @Inject constructor(
    private val keysProvider : KeysProvider
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestWithHeaders = originalRequest.newBuilder()
            .addHeader("X-Parse-Application-Id", keysProvider.apiKey)
            .addHeader("X-Parse-Application-Id", keysProvider.appId)
            .build()
        return chain.proceed(requestWithHeaders)
    }


}