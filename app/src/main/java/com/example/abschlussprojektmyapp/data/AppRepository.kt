package com.example.abschlussprojektmyapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abschlussprojektmyapp.data.model.cryptoapi.CryptoCurrency
import com.example.abschlussprojektmyapp.data.model.jokeapi.Joke
import com.example.abschlussprojektmyapp.data.model.cryptoapi.MarketModel
import com.example.abschlussprojektmyapp.data.remote.Api
import com.example.abschlussprojektmyapp.data.remote.JokeApi
import com.example.abschlussprojektmyapp.data.remote.NewsApi
import java.lang.Exception

/**

 * Konstante, die den Tag für das Repository definiert.
 */
const val TAG = "Repository"

/**

 * Diese Klasse repräsentiert ein Repository, das Daten von der API und der Datenbank abruft und verwaltet.
 * @param api Die Schnittstelle zur Kommunikation mit der API.
 * @param database Die Datenbank, in der die Daten gespeichert werden.
 */
class AppRepository(private val api: JokeApi, private val api2: Api, private val api3: NewsApi) { //private val AbschlussprojektMyAppdatabase: Database


    //private val number = ""
    //private val key = ""

    private val _joke = MutableLiveData<List<Joke>>()
    val joke: LiveData<List<Joke>>
        get() = _joke

    suspend fun getJokes() {
        try {
            _joke.value = api.retrofitService.getJokes().jokes
        } catch (e: Exception) {
            Log.d("Repo", "$e")
        }
    }

    private val _market = MutableLiveData<MarketModel>() //Private Variable, die eine Liste von MarketModel als MutableLiveData hält
    val market: LiveData<MarketModel> //Öffentliche Variable, die eine Liste von MarketModel als LiveData zurückgibt
        get() = _market

    suspend fun getMarketData(){
        try {
            _market.postValue(api2.retrofitService.getMarketData().body())
            Log.d(TAG, "${_market.value}")
        } catch (e: Exception) {
            Log.e(TAG, "$e")
        }
    }

    private val _crypto = MutableLiveData<List<CryptoCurrency>>()
    val crypto: LiveData<List<CryptoCurrency>>
        get() = _crypto

}






