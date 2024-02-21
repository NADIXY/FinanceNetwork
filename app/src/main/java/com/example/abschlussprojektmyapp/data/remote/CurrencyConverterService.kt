package com.example.abschlussprojektmyapp.data.remote

import android.util.Log
import com.example.abschlussprojektmyapp.data.model.currencyapi.ExchangeRatesX
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL4 = "https://v6.exchangerate-api.com/v6/"

private val moshi4 =
    Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
private val logger: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level =
        HttpLoggingInterceptor.Level.BODY
}
private val httpClient = OkHttpClient.Builder()
    .addInterceptor(logger)
    .build()

val retrofit4 =
    Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi4)).client(httpClient)
        .baseUrl(BASE_URL4)
        .build()
interface ApiService {
    @GET("85c937dc36750609ccd39937/latest/EUR")
    suspend fun getExchangeRates(): ExchangeRatesX
}

object CurrencyApi {
    val retrofitService: ApiService by lazy { retrofit4.create(ApiService::class.java) }.apply {
        Log.d("Api_Call_Test4", "Making API call to getExchangeRates() ")
    }
}







