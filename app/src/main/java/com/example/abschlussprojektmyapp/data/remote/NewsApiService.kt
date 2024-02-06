package com.example.abschlussprojektmyapp.data.remote

import com.example.abschlussprojektmyapp.data.model.newsapi.NewsResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/*
Yahoo Finance News API
Website: https://developer.yahoo.com/finance/news/
Dokumentation: https://developer.yahoo.com/finance/news/
Endpunkt: https://api.news.yahoo.com/api/v1/news
Authentifizierungsmethode: API-Key
 */

const val BASE_URL3 = "https://eodhd.com/api/"

private val moshi3 =
    Moshi.Builder() //Eine Instanz von Moshi, die zum Konvertieren von JSON in Kotlin-Objekte verwendet wird.
        .add(KotlinJsonAdapterFactory()) //Fügt den KotlinJsonAdapterFactory zur Moshi-Instanz hinzu, um die Verwendung von Kotlin-spezifischen Typen zu ermöglichen.
        .build() //Erstellt die Moshi-Instanz mit den hinzugefügten Adaptern und Konfigurationen.

//Then Build Retrofit Object and passing in moshi object created above
val retrofit3 =
    Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi3)) //for parsing KotlinObjects i.e.
        // picture of the day
        .baseUrl(BASE_URL3)
        .build()

// Das Interface definiert unsere API Calls, Schnittstelle die die API-Endpunkteiniert.
interface NewsApiService {
    @GET("news")
    suspend fun getNews(
        @Query("s") symbol: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("api_token") apiToken: String,
        @Query("fmt") format: String
    ): Response<NewsResponse>
}

object NewsApi {
    val retrofitService: NewsApiService by lazy { retrofit2.create(NewsApiService::class.java) }.apply {

    }
}