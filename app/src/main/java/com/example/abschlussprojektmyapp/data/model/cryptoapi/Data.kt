package com.example.abschlussprojektmyapp.data.model.cryptoapi

import com.example.abschlussprojektmyapp.data.model.cryptoapi.CryptoCurrency

/**

 *Eine Datenklasse, die eine Liste von Kryptowährungen und die Gesamtanzahl der Kryptowährungen enthält.
 *@property cryptoCurrencyList Die Liste der Kryptowährungen.
 *@property totalCount Die Gesamtanzahl der Kryptowährungen als Zeichenkette.
 */
data class Data(
    val cryptoCurrencyList: List<CryptoCurrency>,
    val totalCount: String
)