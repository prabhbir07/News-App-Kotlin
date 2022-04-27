package com.prabhbir07.newsapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.prabhbir07.newsapp.R
import com.prabhbir07.newsapp.db.ArticleDatabase
import com.prabhbir07.newsapp.repository.NewsRepository
import com.prabhbir07.newsapp.viewModel.NewsViewModel
import com.prabhbir07.newsapp.viewModel.NewsViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)



        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)

        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel::class.java)

       // bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())

        val navHostFragment= supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController= navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)

    }
}