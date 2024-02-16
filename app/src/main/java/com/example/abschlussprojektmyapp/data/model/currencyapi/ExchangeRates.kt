package com.example.abschlussprojektmyapp.data.model.currencyapi

data class ExchangeRates(
    val baseCurrency: String,
    val rates: Map<String, Double>
)
