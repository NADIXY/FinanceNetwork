package com.example.abschlussprojektmyapp

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.abschlussprojektmyapp.data.AppRepository
import com.example.abschlussprojektmyapp.data.local.getDatabase
import com.example.abschlussprojektmyapp.data.model.Note
import com.example.abschlussprojektmyapp.data.model.Profile
import com.example.abschlussprojektmyapp.data.model.newsapi.Article
import com.example.abschlussprojektmyapp.data.remote.Api
import com.example.abschlussprojektmyapp.data.remote.CurrencyApi
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
class MainViewModel(application: Application) : AndroidViewModel(application) {

    // Instanz von Firebase Authentication
    // Ersetzt in diesem Fall ein Repository
    val auth = Firebase.auth

    // Instanz von Firebase Firestore
    val firestore = Firebase.firestore

    // Instanz und Referenz von Firebase Storage
    val storage = Firebase.storage
    private val storageRef = storage.reference

    private val _user: MutableLiveData<FirebaseUser?> = MutableLiveData()
    val user: LiveData<FirebaseUser?>
        get() = _user

    //Das profile Document enthält ein einzelnes Profil(das des eingeloggten Users)
    //Document ist wie ein Objekt
    lateinit var profileRef: DocumentReference

    init {
        setupUserEnv()
    }

    fun setupUserEnv() {

        _user.value = auth.currentUser

        auth.currentUser?.let { firebaseUser ->

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

    fun sendPasswordReset(email: String) {
        auth.sendPasswordResetEmail(email)

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



    // Funktion um Bild in den Firebase Storage hochzuladen
    fun uploadImage(uri: Uri) {
        // Erstellen einer Referenz und des Upload Tasks
        val imageRef = storageRef.child("images/${auth.currentUser!!.uid}/profilePic")
        val uploadTask = imageRef.putFile(uri)

        // Wenn UploadTask ausgeführt und erfolgreich ist, wird die Download-Url des Bildes an die setUserImage Funktion weitergegeben
        uploadTask.addOnCompleteListener {
            imageRef.downloadUrl.addOnCompleteListener {
                if (it.isSuccessful) {
                    setUserImage(it.result)
                }
            }
        }
    }

    // Funktion um Url zu neue hochgeladenem Bild im Firestore dem aktuellen Userprofil hinzuzufügen
    private fun setUserImage(uri: Uri) {
        profileRef.update("profilePicture", uri.toString())
    }

    // Funktion um das Profil eines Users zu updaten
    fun updateProfile(profile: Profile) {
        profileRef.set(profile)
    }



    private val appRepository =
        AppRepository(Api, NewsApi,CurrencyApi,getDatabase(application))

    val market =
        appRepository.market //Variable, die das market-Objekt aus der Repository-Klasse holt.
    val crypto =
        appRepository.crypto //Variable, die das crypto-Objekt aus der Repository-Klasse holt.
    val newsList =
        appRepository.newsList //Variable, die das newsList-Objekt aus der Repository-Klasse holt.

    val exchangeRates = appRepository.exchangeRates //Variable, die das exchangeRates-Objekt aus der Repository-Klasse holt.

    val notes = appRepository.notes //Variable, die die Notiz-Objekte aus der Repository-Klasse holt.

    fun getMarketData() {
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

    //Funktion um das aktuell ausgewählte Article zu setzen
    fun setSelectedItem(item: Article) {
        _selectedItem.value = item

    }

    fun getExchangeRates() { //Fragment
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.getExchangeRates() //Repo
        }
    }

    /**

    Speichert eine Notiz zu einer Nachricht.
    @param message Die Nachricht, zu der die Notiz gespeichert werden soll.
     */
    fun saveNote(article: Article) {
        val newNote = Note(null, article.publishedAt, article.title,article.author.toString())
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.saveNote(newNote)
        }
    }

    /**

    Löscht eine Notiz.
    @param note Die zu löschende Notiz.
     */
    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.deleteNote(note)
        }
    }




}




















