package com.example.abschlussprojektmyapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abschlussprojektmyapp.data.local.BusinessNewsDatabase
import com.example.abschlussprojektmyapp.data.model.SavedNews
import com.example.abschlussprojektmyapp.data.model.cryptoapi.CryptoCurrency
import com.example.abschlussprojektmyapp.data.model.cryptoapi.MarketModel
import com.example.abschlussprojektmyapp.data.model.currencyapi.ExchangeRatesX
import com.example.abschlussprojektmyapp.data.model.newsapi.News
import com.example.abschlussprojektmyapp.data.remote.Api
import com.example.abschlussprojektmyapp.data.remote.CurrencyApi
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
class AppRepository(
    private val apiCrypto: Api,
    private val apiNews: NewsApi,
    private val apiCurrency: CurrencyApi,
    private val database: BusinessNewsDatabase

) {

    private val _crypto = MutableLiveData<List<CryptoCurrency>>()
    val crypto: LiveData<List<CryptoCurrency>>
        get() = _crypto

    private val _market =
        MutableLiveData<MarketModel>() //Private Variable, die eine Liste von MarketModel als MutableLiveData hält
    val market: LiveData<MarketModel> //Öffentliche Variable, die eine Liste von MarketModel als LiveData zurückgibt
        get() = _market

    suspend fun getMarketData() {
        try {
            _market.postValue(apiCrypto.retrofitService.getMarketData().body())
            Log.d(TAG, "${_market.value}")
        } catch (e: Exception) {
            Log.e(TAG, "$e")
        }
    }

    private val _newsList = MutableLiveData<News>()
    val newsList: LiveData<News>
        get() = _newsList


    private val key = "b3499f61db0b40649d0b3aed4238bef2"

    suspend fun getBusinessNews() {
        try {
            _newsList.postValue(apiNews.retrofitService.getBusinessNews("us", "business", key).body())
        } catch (e: Exception) {
            Log.d("Repo2", "$e")
        }
    }

    private val _exchangeRates = MutableLiveData<ExchangeRatesX>()
    val exchangeRates: LiveData<ExchangeRatesX>
        get() = _exchangeRates

    suspend fun getExchangeRates() {
        try {
            _exchangeRates.postValue(apiCurrency.retrofitService.getExchangeRates())
            Log.d(TAG,apiCurrency.retrofitService.getExchangeRates().toString())
        } catch (e: Exception) {
            Log.d("Repo3", "$e")
        }
    }


    val savedNews = database.dao.getSavedNews()


    suspend fun saveSavedNews(article: SavedNews) {
        database.dao.insertSavedNews(article)
    }

    suspend fun deleteSavedNews(article: SavedNews) {
        database.dao.deleteSavedNews(article)
    }

}





























