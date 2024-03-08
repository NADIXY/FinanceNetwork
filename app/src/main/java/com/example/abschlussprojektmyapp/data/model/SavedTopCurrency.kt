package com.example.abschlussprojektmyapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.abschlussprojektmyapp.data.model.cryptoapi.Quote
import com.squareup.moshi.Json

@Entity
data class SavedTopCurrency(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String

)

