package com.example.abschlussprojektmyapp.data.model

/**

 *Datenklasse, die ein Benutzerprofil repräsentiert.
 *@property isPremium Gibt an, ob der Benutzer ein Premium-Konto hat. Standardmäßig ist es auf false gesetzt.
 *@property username Der Benutzername des Profils. Standardmäßig ist es ein leerer String.
 *@property profilePicture Der Dateipfad zum Profilbild des Benutzers. Standardmäßig ist es ein leerer String.
 */
data class Profile(
    //val isPremium: String = false,
    val username: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val profilePicture: String = ""

) {

}

