package com.example.abschlussprojektmyapp.data.model

/**

 *Datenklasse zur Speicherung von Audit-Informationen.
 *@param auditStatus Der Status des Audits als Ganzzahl.
 *@param auditTime Die Zeit des Audits als Zeichenkette.
 *@param auditor Der Name des Auditors als Zeichenkette.
 *@param coinId Die ID der Münze als Zeichenkette.
 *@param contractAddress Die Vertragsadresse als Zeichenkette.
 *@param contractPlatform Die Plattform des Vertrags als Zeichenkette.
 *@param reportUrl Die URL des Audit-Berichts als Zeichenkette.
 *@param score Die Bewertung des Audits als Zeichenkette.
 */
data class AuditInfo(
    val auditStatus: Int,
    val auditTime: String,
    val auditor: String,
    val coinId: String,
    val contractAddress: String,
    val contractPlatform: String,
    val reportUrl: String,
    val score: String
)