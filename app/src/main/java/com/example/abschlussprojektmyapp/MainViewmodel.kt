package com.example.abschlussprojektmyapp

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.abschlussprojektmyapp.data.AppRepository
import com.example.abschlussprojektmyapp.data.model.Profile
import com.example.abschlussprojektmyapp.data.model.newsapi.Article
import com.example.abschlussprojektmyapp.data.remote.Api
import com.example.abschlussprojektmyapp.data.remote.JokeApi
import com.example.abschlussprojektmyapp.data.remote.NewsApi
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


    private val appRepository = AppRepository(JokeApi, Api, NewsApi) //getDatabase(application)

    val data = appRepository.joke
    val market = appRepository.market //Variable, die das market-Objekt aus der Repository-Klasse holt.
    val crypto = appRepository.crypto //Variable, die das crypto-Objekt aus der Repository-Klasse holt.
    val newsList = appRepository.newsList //Variable, die das newsList-Objekt aus der Repository-Klasse holt.



    fun loadData() {
        viewModelScope.launch {
            appRepository.getJokes()
        }
    }

    fun getMarketData() {
        //val position = requireArguments().getInt("position")
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.getMarketData()
        }
    }


    fun getBusinessNews() {
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.getBusinessNews()
        }

    }

    private var _selectedItem = MutableLiveData<Article>()
    val selectedItem: LiveData<Article>
        get() = _selectedItem

    //Funktion um das aktuell ausgewählte Animal zu setzen

    fun setSelectedItem(item: Article) {
        _selectedItem.value = item

    }

}










