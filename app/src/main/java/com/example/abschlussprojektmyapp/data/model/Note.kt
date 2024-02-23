package com.example.abschlussprojektmyapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(

    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val publishedAt: String,
    val title: String,
    val author: String
)