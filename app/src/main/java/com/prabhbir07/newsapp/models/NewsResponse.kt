package com.prabhbir07.newsapp.models

import com.prabhbir07.newsapp.models.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)