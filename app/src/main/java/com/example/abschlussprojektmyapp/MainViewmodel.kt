package com.example.abschlussprojektmyapp

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.abschlussprojektmyapp.data.Repository
import com.example.abschlussprojektmyapp.data.TAG
import com.example.abschlussprojektmyapp.data.model.CryptoCurrency
import com.example.abschlussprojektmyapp.data.model.MarketModel
import com.example.abschlussprojektmyapp.data.remote.Api
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**

ViewModel-Klasse, die von AndroidViewModel erbt und eine Referenz auf die Anwendung enthält.
@param application Die Anwendung, auf die zugegriffen werden soll.
 */
class MainViewmodel(application: Application) : AndroidViewModel(application) {

    /**

    Private Variable, das die Repository-Klasse initialisiert.
    @property repository Die Instanz der Repository-Klasse.
     */
    private val repository = Repository(Api) //getDatabase(application)

    val auth = Firebase.auth
    val firestore = Firebase.firestore
    val storage = Firebase.storage

    private val _user: MutableLiveData<FirebaseUser?> = MutableLiveData()
    val user: LiveData<FirebaseUser?>
        get() = _user

    lateinit var profileRef: DocumentReference

    init {
        setupUserEnv()
    }

    fun setupUserEnv(){

        _user.value = auth.currentUser

        auth.currentUser?.let {firebaseUser ->

            profileRef = firestore.collection("user").document(firebaseUser.uid)

        }

    }

    fun register(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {

                setupUserEnv()

                val newProfile = Profile()
                profileRef.set(newProfile)

            } else {

            }
        }

    }

    fun login(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {

                setupUserEnv()
            } else {

            }
        }
    }

    fun logout() {

        auth.signOut()
        setupUserEnv()
    }

    fun uploadProfilePicture(uri: Uri) {

        val imageRef = storage.reference.child("images/${auth.currentUser!!.uid}/profilePicture")
        imageRef.putFile(uri).addOnCompleteListener {
            if (it.isSuccessful) {
                imageRef.downloadUrl.addOnCompleteListener { finalImageUrl ->
                    profileRef.update("profilePicture", finalImageUrl.result.toString())
                }
            }
        }

    }

    /**

    Variable, die das market-Objekt aus der Repository-Klasse holt.
    @property market Das market-Objekt aus der Repository-Klasse.
     */
    val market = repository.market
    //val market: LiveData<List<MarketModel>> = repository.market

    val crypto: LiveData<List<CryptoCurrency>> = repository.crypto
    //val crypto = repository.crypto //Variable, die das CryptoCurrency-Objekt aus der Repository-Klasse holt.


    fun getMarketData() {
        //val position = requireArguments().getInt("position")
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMarketData()

        }
    }



    /*fun getMarketData(position: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getMarketData()
            } catch (e: Exception) {
                Log.e("ERROR", "Error getting market data: $e")
            }
        }
    }

     */

}










