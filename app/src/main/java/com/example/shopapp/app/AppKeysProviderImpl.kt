package com.example.shopapp.app

import com.example.shopapp.BuildConfig
import com.example.shopapp.core.network.secrets.KeysProvider
import jakarta.inject.Inject

class AppKeysProviderImpl @Inject constructor() : KeysProvider {
    override val apiKey: String
        get() = BuildConfig.API_KEY
    override val appId: String
        get() = BuildConfig.APP_ID

}