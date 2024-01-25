package com.example.abschlussprojektmyapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage

class MainViewmodel : ViewModel() {

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

    //region FirebaseUserManagement

    //Richtet die Variablen ein die erst eingerichtet werden können
    //wenn der User eingeloggt ist
    fun setupUserEnv(){

        _user.value = auth.currentUser

        //Alternative Schreibweise um auf null Werte zu überprüfen
        auth.currentUser?.let {firebaseUser ->

            profileRef = firestore.collection("user").document(firebaseUser.uid)

        }

    }

    fun register(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                //User wurde erstellt
                setupUserEnv()

                val newProfile = Profile()
                profileRef.set(newProfile)

            } else {
                //Fehler aufgetreten
            }
        }

    }

    fun login(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                //User wurde eingeloggt
                setupUserEnv()
            } else {
                //Fehler aufgetreten
            }
        }
    }

    fun logout() {

        auth.signOut()
        setupUserEnv()

    }

    //endregion
}