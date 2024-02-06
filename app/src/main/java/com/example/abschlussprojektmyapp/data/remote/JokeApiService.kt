package com.example.abschlussprojektmyapp.data.remote

import com.example.abschlussprojektmyapp.data.model.jokeapi.ServerResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://v2.jokeapi.dev/joke/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface JokeApiService {
    @GET("Any?lang=de&blacklistFlags=nsfw,religious,political,racist,sexist,explicit&type=twopart&amount=10")
    suspend fun getJokes(): ServerResponse

}

object JokeApi {
    val retrofitService: JokeApiService by lazy { retrofit.create(JokeApiService::class.java) }
}
