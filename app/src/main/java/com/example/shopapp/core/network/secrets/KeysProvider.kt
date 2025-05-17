package com.example.shopapp.core.network.secrets

interface KeysProvider {
    val apiKey: String
    val appId : String
}