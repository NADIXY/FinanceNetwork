package com.example.abschlussprojektmyapp.data.model.currencyapi

data class ExchangeRatesX(
    val base_code: String,
    val conversion_rates: Map<String, Double>?,
    val documentation: String,
    val result: String,
    val terms_of_use: String,
    val time_last_update_unix: Int,
    val time_last_update_utc: String,
    val time_next_update_unix: Int,
    val time_next_update_utc: String
)