package com.prabhbir07.newsapp.repository

import com.prabhbir07.newsapp.api.RetrofitInstance
import com.prabhbir07.newsapp.db.ArticleDatabase
import com.prabhbir07.newsapp.models.Article

class NewsRepository (
    val db :ArticleDatabase){

    suspend fun getBreakingNews(countryCode: String, pageNumber:Int)=
        RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int)=
        RetrofitInstance.api.searchForNews(searchQuery,pageNumber)



}