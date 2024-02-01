package com.example.abschlussprojektmyapp.data.model

/**

 *Datenklasse, die eine Kryptowährungsquote repräsentiert.
 *@property dominance Dominanz der Kryptowährung
 *@property fullyDilluttedMarketCap Vollständig verwässerte Marktkapitalisierung
 *@property lastUpdate Zeitpunkt des letzten Updates
 *@property marketCap Marktkapitalisierung
 *@property marketCapByTotalSupply Marktkapitalisierung nach Gesamtangebot
 *@property name Name der Kryptowährung
 *@property percentChange1h Prozentuale Veränderung in 1 Stunde
 *@property percentChange24h Prozentuale Veränderung in 24 Stunden
 *@property percentChange30d Prozentuale Veränderung in 30 Tagen
 *@property percentChange60d Prozentuale Veränderung in 60 Tagen
 *@property percentChange7d Prozentuale Veränderung in 7 Tagen
 *@property percentChange90d Prozentuale Veränderung in 90 Tagen
 *@property price Preis der Kryptowährung
 *@property turnover Umsatz
 *@property tvl Gesamtwert des gesperrten Vermögens (TVL)
 *@property volume24h Handelsvolumen der letzten 24 Stunden
 *@property ytdPriceChangePercentage Prozentsatz der Preisänderung seit Jahresbeginn
 */
data class Quote(
    val dominance: Double,
    val fullyDilluttedMarketCap: Double,
    val lastUpdate: String,
    val marketCap: Double,
    val marketCapByTotalSupply: Double,
    val name: String,
    val percentChange1h: Double,
    val percentChange24h: Double,
    val percentChange30d: Double,
    val percentChange60d: Double,
    val percentChange7d: Double,
    val percentChange90d: Double,
    val price: Double,
    val turnover: Double,
    val tvl: Double,
    val volume24h: Double,
    val ytdPriceChangePercentage: Double
)