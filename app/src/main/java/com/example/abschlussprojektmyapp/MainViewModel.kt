package com.example.abschlussprojektmyapp

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.abschlussprojektmyapp.data.AppRepository
import com.example.abschlussprojektmyapp.data.local.getDatabase
import com.example.abschlussprojektmyapp.data.local.getTopCurrencyDatabase
import com.example.abschlussprojektmyapp.data.model.SavedNews
import com.example.abschlussprojektmyapp.data.model.Profile
import com.example.abschlussprojektmyapp.data.model.SavedTopCurrency
import com.example.abschlussprojektmyapp.data.model.cryptoapi.CryptoCurrency
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

    // LiveData um den aktuellen User zu halten
    // Initialwert ist in diesem Fall firebaseAuth.currentUser
    // Das gewährleistet, dass der User sofort wieder eingeloggt ist sollte er sich bereits einmal eingeloggt haben
    // LiveData kann auch "null" sein (Wenn der User nicht eingeloggt ist)
    private var _user = MutableLiveData<FirebaseUser?>(auth.currentUser)
    val user: LiveData<FirebaseUser?>
        get() = _user

    //Das profile Document enthält ein einzelnes Profil(das des eingeloggten Users)
    //Document ist wie ein Objekt
    //lateinit var profileRef: DocumentReference

    var profileRef: DocumentReference? = null

    init {
        setupUserEnv()
    }

    fun setupUserEnv() {

        _user.value = auth.currentUser

        auth.currentUser?.let { firebaseUser ->
            if (profileRef == null) {

                profileRef = firestore.collection("profiles").document(firebaseUser.uid)
            }
        }
    }

    // Funktion um neuen User zu erstellen
    fun register(email: String, password: String) {
        // Firebase-Funktion um neuen User anzulegen
        // CompleteListener sorgt dafür, dass wir anschließend feststellen können, ob das funktioniert hat
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { authResult ->
            if (authResult.isSuccessful) {
                // Wenn Registrierung erfolgreich ist senden wir eine Email um die Email-Adresse zu bestätigen
                auth.currentUser?.sendEmailVerification()
                // Die Profil-Referenz wird jetzt gesetzt, da diese vom aktuellen User abhängt
                profileRef = firestore.collection("profiles").document(auth.currentUser!!.uid)
                // Ein neues, leeres Profil wird für jeden User erstellt der zum ersten mal einen Account für die App anlegt
                profileRef!!.set(Profile())
                // Danach führen wir logout Funktion aus, da beim Erstellen eines Users dieser sofort eingeloggt wird
                logout()
            } else {
                // Log, falls Fehler beim Erstellen eines Users auftritt
                Log.e("FIREBASE", "${authResult.exception}")
            }
        }
    }

    fun login(email: String, password: String) {
        // Firebase-Funktion um User einzuloggen
        // CompleteListener sorgt dafür, dass wir anschließend feststellen können, ob das funktioniert hat
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { authResult ->
            if (authResult.isSuccessful) {

                // Überprüfung, ob User bereits Email verifiziert hat
                if (auth.currentUser!!.isEmailVerified) {
                    // Wenn Email verifiziert, wird LiveData mit dem eingeloggten User befüllt
                    // Das triggert dann die Navigation im SignInFragment

                    // Die Profil-Referenz wird jetzt gesetzt, da diese vom aktuellen User abhängt
                    profileRef = firestore.collection("profiles").document(auth.currentUser!!.uid)

                    _user.value = auth.currentUser
                } else {
                    // Wenn User zwar exisitiert und Eingaben stimmen aber User seine Email noch nicht bestätigt hat
                    // wird User wieder ausgeloggt und eine Fehlermeldung ausgegeben
                    Log.e("FIREBASE", "User not verified")
                    logout()
                }

            } else {
                // Log, falls Fehler beim Login eines Users auftritt
                Log.e("FIREBASE", "${authResult.exception}")
            }
        }
    }

    fun sendPasswordReset(email: String) {
        auth.sendPasswordResetEmail(email)

    }

    fun logout() {

        auth.signOut()

        // Danach wird der Wert der currentUser LiveData auf den aktuellen Wert des Firebase-CurrentUser gesetzt
        // Nach Logout ist dieser Wert null, also ist auch in der LiveData danach der Wert null gespeichert
        // Dies triggert die Navigation aus dem HomeFragment zurück zum SignInFragment
        _user.value = auth.currentUser
    }

    fun uploadProfilePicture(uri: Uri) {

        val imageRef = storage.reference.child("images/${auth.currentUser!!.uid}/profilePicture")
        imageRef.putFile(uri).addOnCompleteListener {
            if (it.isSuccessful) {
                imageRef.downloadUrl.addOnCompleteListener { finalImageUrl ->
                    profileRef?.update("profilePicture", finalImageUrl.result.toString())
                }
            }
        }

    }

    // Funktion um Eingabefelder des Profils eines Users zu updaten
    fun updateProfile(profile: Profile) {
        profileRef?.update(
            mapOf(
                "firstName" to profile.firstName,
                "lastName" to profile.lastName,
                "number" to profile.number,
            )
        )
    }

    private val appRepository =
        AppRepository(
            Api,
            NewsApi,
            CurrencyApi,
            getDatabase(application),
            getTopCurrencyDatabase(application)
        )

    val market =
        appRepository.market

    //Variable, die das market-Objekt aus der Repository-Klasse holt.
    val newsList =
        appRepository.newsList

    val exchangeRates =
        appRepository.exchangeRates

    //Variable, die die savedNews-Objekte aus der Repository-Klasse holt.
    val savedNews =
        appRepository.savedNews

    val savedTopCurrency =
        appRepository.savedTopCurrency

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

    fun getExchangeRates() {
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.getExchangeRates()
        }
    }

    fun saveSavedNews(article: Article) {
        val newSavedNews =
            SavedNews(null, article.publishedAt, article.title, article.author.toString())
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.saveSavedNews(newSavedNews)
        }
    }

    fun deleteSavedNews(savedNews: SavedNews) {
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.deleteSavedNews(savedNews)
        }
    }

    fun saveTopCurrency(cryptoCurrency: CryptoCurrency) {
        val newSavedTopCurrency = SavedTopCurrency(cryptoCurrency.id,cryptoCurrency.name
        )

        viewModelScope.launch(Dispatchers.IO) {
            appRepository.saveTopCurrency(newSavedTopCurrency)
        }

    }

    fun deleteTopCurrency(savedTopCurrency: SavedTopCurrency) {
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.deleteTopCurrency(savedTopCurrency)
        }

    }

}