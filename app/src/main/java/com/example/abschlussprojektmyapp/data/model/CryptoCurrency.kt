package com.example.abschlussprojektmyapp.data.model

/**

 *Eine Datenklasse, die Informationen über eine Kryptowährung enthält.
 *@param auditInfoList Eine Liste von Audit-Informationen
 *@param circulatingSupply Die zirkulierende Versorgungsmenge
 *@param cmcRank Der Rang der Kryptowährung auf CoinMarketCap
 *@param dateAdded Das Hinzufügungsdatum der Kryptowährung
 *@param id Die eindeutige ID der Kryptowährung
 *@param isActive Ein Wert, der angibt, ob die Kryptowährung aktiv ist
 *@param isAudited Ein Wert, der angibt, ob die Kryptowährung auditiert wurde
 *@param lastUpdate Das Datum des letzten Updates der Kryptowährungsinformationen
 *@param marketPairCount Die Anzahl der Handelspaare für die Kryptowährung
 *@param maxSupply Die maximale Versorgungsmenge der Kryptowährung
 *@param name Der Name der Kryptowährung
 *@param platform Die Plattform, auf der die Kryptowährung basiert
 *@param quotes Eine Liste von Angeboten für die Kryptowährung
 *@param selfReportedCirculatingSupply Die selbst gemeldete zirkulierende Versorgungsmenge
 *@param slug Der Slug (eindeutiger Bezeichner) der Kryptowährung
 *@param symbol Das Symbol (Abkürzung) der Kryptowährung
 *@param tags Eine Liste von Tags, die die Kryptowährung beschreiben
 *@param totalSupply Die Gesamtversorgungsmenge der Kryptowährung
 */
data class CryptoCurrency(
    val auditInfoList: List<AuditInfo>?,
    val circulatingSupply: Double,
    val cmcRank: Double,
    val dateAdded: String,
    val id: Int,
    val isActive: Double,
    val isAudited: Boolean,
    val lastUpdate: String?,
    val marketPairCount: Double,
    val maxSupply: Double?,
    val name: String,
    //val platform: Platform,
    val quotes: List<Quote>,
    val selfReportedCirculatingSupply: Double,
    val slug: String,
    val symbol: String,
    val tags: List<String>,
    val totalSupply: Double
)