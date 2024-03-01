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
import com.example.abschlussprojektmyapp.data.model.Chat
import com.example.abschlussprojektmyapp.data.model.Message
import com.example.abschlussprojektmyapp.data.model.Note
import com.example.abschlussprojektmyapp.data.model.Profile
import com.example.abschlussprojektmyapp.data.model.newsapi.Article
import com.example.abschlussprojektmyapp.data.remote.Api
import com.example.abschlussprojektmyapp.data.remote.CurrencyApi
import com.example.abschlussprojektmyapp.data.remote.NewsApi
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.CollectionReference
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
    //private val storageRef = storage.reference


    // LiveData um den aktuellen User zu halten
    // Initialwert ist in diesem Fall firebaseAuth.currentUser
    // Das gewährleistet, dass der User sofort wieder eingeloggt ist sollte er sich bereits einmal eingeloggt haben
    // LiveData kann auch "null" sein (Wenn der User nicht eingeloggt ist)
    private var _user = MutableLiveData<FirebaseUser?>(auth.currentUser)
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
                profileRef.set(Profile())
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
                    // Das triggert dann die Navigation im LoginFragment

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
        // Dies triggert die Navigation aus dem HomeFragment zurück zum LoginFragment
        _user.value = auth.currentUser
        //setupUserEnv()
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

    // Funktion um Url zu neue hochgeladenem Bild im Firestore dem aktuellen Userprofil hinzuzufügen
    private fun setUserImage(uri: Uri) {
        profileRef.update("profilePicture", uri.toString())
    }

    // Funktion um das Profil eines Users zu updaten
    fun updateProfile(profile: Profile) {
        profileRef.set(profile)
    }

    val chatsRef = firestore.collection("chats")

    fun createChat(userId: String) {

        val chat = Chat(
            listOf(
                userId,
                auth.currentUser!!.uid
            )
        )

        firestore.collection("chats").add(chat)
    }

    fun addMessageToChat(message: String, chatId: String) {

        val message = Message(
            content = message,
            senderId = auth.currentUser!!.uid
        )

        firestore.collection("chats").document(chatId).collection("messages").add(message)
    }

    fun getMessageRef(chatId: String): CollectionReference {
        return firestore.collection("chats").document(chatId).collection("messages")
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




















