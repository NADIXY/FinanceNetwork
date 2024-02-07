package com.example.abschlussprojektmyapp.data.model.newsapi

data class News(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)