package com.example.abschlussprojektmyapp

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.abschlussprojektmyapp.data.AppRepository
import com.example.abschlussprojektmyapp.data.model.CryptoCurrency
import com.example.abschlussprojektmyapp.data.remote.Api
import com.example.abschlussprojektmyapp.data.remote.JokeApi
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




    private val appRepository = AppRepository(JokeApi, Api ) //Api, getDatabase(application)
    val data = appRepository.joke

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            appRepository.getJokes()
        }
    }

    /**

    Variable, die das market-Objekt aus der Repository-Klasse holt.
    @property market Das market-Objekt aus der Repository-Klasse.
     */
    val market = appRepository.market
    //val market: LiveData<List<MarketModel>> = repository.market

    val crypto: LiveData<List<CryptoCurrency>> = appRepository.crypto
    //val crypto = repository.crypto //Variable, die das CryptoCurrency-Objekt aus der Repository-Klasse holt.


    fun getMarketData() {
        //val position = requireArguments().getInt("position")
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.getMarketData()
        }
    }


}










