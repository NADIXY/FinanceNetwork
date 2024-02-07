package com.example.abschlussprojektmyapp.data.remote

import android.util.Log
import com.example.abschlussprojektmyapp.data.model.newsapi.News
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL3 = "https://newsapi.org/"

private val moshi3 =
    Moshi.Builder() //Eine Instanz von Moshi, die zum Konvertieren von JSON in Kotlin-Objekte verwendet wird.
        .add(KotlinJsonAdapterFactory()) //Fügt den KotlinJsonAdapterFactory zur Moshi-Instanz hinzu, um die Verwendung von Kotlin-spezifischen Typen zu ermöglichen.
        .build() //Erstellt die Moshi-Instanz mit den hinzugefügten Adaptern und Konfigurationen.

//Dies ist eine private Variable, die einen HttpLoggingInterceptor-Objekt enthält.
//Setzt das Level des HttpLoggingInterceptor auf BODY, um den gesamten Body der HTTP-Anfragen und -Antworten zu loggen.
private val logger: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY //BASIC oder HEADERS (Andere verfügbare Optionen sind BASIC und HEADERS)
    }

private val httpClient = OkHttpClient.Builder() //Erstellt einen neuen OkHttpClient Builder.
        .addInterceptor(logger) //Fügt einen Interceptor zum OkHttpClient Builder hinzu.
        .build() //Erstellt einen neuen OkHttpClient mit den konfigurierten Einstellungen.

//Then Build Retrofit Object and passing in moshi object created above
val retrofit3 =
    Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi3)) //for parsing KotlinObjects i.e.
        // picture of the day
        .baseUrl(BASE_URL3)
        .build()

// Das Interface definiert unsere API Calls, Schnittstelle die die API-Endpunkteiniert.
interface Service {
    @GET("v2/top-headlines")
    suspend fun getBusinessNews(
        @Query("contry") contry: String,
        @Query("category") category: String,
        @Query("apiKey") apiToken: String,

    ): Response<News>
}

object NewsApi {
    val retrofitService: Service by lazy { retrofit3.create(Service::class.java) }.apply {
        Log.d("Api_Call_Test2", "Making API call to getBusinessNews")

    }
}