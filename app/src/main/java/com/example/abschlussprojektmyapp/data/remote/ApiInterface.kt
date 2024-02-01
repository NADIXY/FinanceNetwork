package com.example.abschlussprojektmyapp.data.remote

import android.util.Log
import com.example.abschlussprojektmyapp.data.model.MarketModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

/**

 * Die Basis-URL für die Verbindung mit dem Server.
 */
const val BASE_URL = "https://api.coinmarketcap.com/"

//First Build Moshi Object
// Moshi konvertiert Serverantworten in Kotlin Objekte
private val moshi =
    Moshi.Builder() //Eine Instanz von Moshi, die zum Konvertieren von JSON in Kotlin-Objekte verwendet wird.
        .add(KotlinJsonAdapterFactory()) //Fügt den KotlinJsonAdapterFactory zur Moshi-Instanz hinzu, um die Verwendung von Kotlin-spezifischen Typen zu ermöglichen.
        .build() //Erstellt die Moshi-Instanz mit den hinzugefügten Adaptern und Konfigurationen.

//Then Build Retrofit Object and passing in moshi object created above
val retrofit =
    Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi)) //for parsing KotlinObjects i.e.
        // picture of the day
        .baseUrl(BASE_URL)
        .build()

// Das Interface definiert unsere API Calls, Schnittstelle die die API-Endpunkteiniert.
interface ApiInterface {
    @GET("data-api/v3/cryptocurrency/listing?start=1&limit=500")
    suspend fun getMarketData(): Response<MarketModel>

}

object Api { //Singleton-Objekt, das die API-Schnittstelle für die Apiüberprüfung bereitstellt.
    //Lazy initialisierter Retrofit-Service für die Kommunikation mit der API.
    val retrofitService: ApiInterface by lazy { retrofit.create(ApiInterface::class.java) }.apply {
        Log.d("Api_Call_Test", "Making API call to getMarketData")
    }
}

















/*
const val BASE_URL = "https://api.coinmarketcap.com/"
private val moshi =
    Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
val retrofit =
    Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()
interface ApiInterface {
    @GET("data-api/v3/cryptocurrency/listing?start=1&limit=500")
    suspend fun getMarketData(): Response<MarketModel>
}
object Api { //Singleton-Objekt, das die API-Schnittstelle für die Apiüberprüfung bereitstellt.
    //Lazy initialisierter Retrofit-Service für die Kommunikation mit der API.
    val retrofitService: ApiInterface by lazy { retrofit.create(ApiInterface::class.java) }.apply {
        Log.d("Api_Call_Test", "Making API call to getMarketData")
    }
}

 */











