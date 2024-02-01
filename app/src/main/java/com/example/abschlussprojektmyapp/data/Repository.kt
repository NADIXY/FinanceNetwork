package com.example.abschlussprojektmyapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abschlussprojektmyapp.data.model.CryptoCurrency
import com.example.abschlussprojektmyapp.data.model.MarketModel
import com.example.abschlussprojektmyapp.data.remote.Api

/**

 * Konstante, die den Tag für das Repository definiert.
 */
const val TAG = "Repository"

/**

 * Diese Klasse repräsentiert ein Repository, das Daten von der API und der Datenbank abruft und verwaltet.
 * @param api Die Schnittstelle zur Kommunikation mit der API.
 * @param database Die Datenbank, in der die Daten gespeichert werden.
 */
class Repository(private val api: Api){ //private val AbschlussprojektMyAppdatabase: Database

    //private val number = ""
    //private val key = ""

    private val _market = MutableLiveData<MarketModel>() //Private Variable, die eine Liste von MarketModel als MutableLiveData hält
    val market: LiveData<MarketModel> //Öffentliche Variable, die eine Liste von MarketModel als LiveData zurückgibt
        get() = _market

    /*suspend fun getMarketData() {
        try {
            val response = api.retrofitService.getMarketData()
            if (response.isSuccessful) {
                _market.value = response.body()?.data // Assuming 'data' is the field in the response containing market data
                Log.d(TAG, "${_market.value}")
            } else {
                // Handle error
                Log.e(TAG, "Failed to get market data: ${response.message()}")
            }
        } catch (e: Exception) {
            // Handle exception
            Log.e(TAG, "$e")
        }
    }

     */

    suspend fun getMarketData(){
        try {
            _market.value = api.retrofitService.getMarketData().body()
            Log.d(TAG, "${_market.value}")
        } catch (e: Exception) {
            Log.e(TAG, "$e")
        }
    }

    private val _crypto = MutableLiveData<List<CryptoCurrency>>() //Private Variable, die eine Liste von Cryptocurrency als MutableLiveData hält
    val crypto: LiveData<List<CryptoCurrency>> //Öffentliche Variable, die eine Liste von Cryptocurrency als LiveData zurückgibt
        get() = _crypto

}



/*

const val TAG = "Repository"
class Repository(private val api: Api){
    private val number = ""
    private val key = ""
    private val _market = MutableLiveData<List<MarketModel>>()
    val market: LiveData<List<MarketModel>>
        get() = _market
    suspend fun getMarketData(){
        try {
            api.retrofitService.getMarketData()
            Log.d(TAG, "${_market.value}")
        } catch (e: Exception) {
            Log.e(TAG, "$e")
        }
    }
    private val _crypto = MutableLiveData<List<CryptoCurrency>>()
    val crypto: LiveData<List<CryptoCurrency>>
        get() = _crypto
}

*/


/*

//Verbesserung:

const val TAG = "Repository"

class Repository(private val api: Api) {
    private val number = ""
    private val key = ""

    private val _market = MutableLiveData<List<MarketModel>>()
    val market: LiveData<List<MarketModel>> get() = _market

    private val _crypto = MutableLiveData<List<CryptoCurrency>>()
    val crypto: LiveData<List<CryptoCurrency>> get() = _crypto

    suspend fun getMarketData() {
        try {
            val response = api.retrofitService.getMarketData()
            if (response.isSuccessful) {
                _market.value = response.body()?.data // Assuming 'data' is the field in the response containing market data
                Log.d(TAG, "${_market.value}")
            } else {
                // Handle error
                Log.e(TAG, "Failed to get market data: ${response.message()}")
            }
        } catch (e: Exception) {
            // Handle exception
            Log.e(TAG, "$e")
        }
    }
}

 */



