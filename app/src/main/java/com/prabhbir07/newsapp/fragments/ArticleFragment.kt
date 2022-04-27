package com.prabhbir07.newsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.prabhbir07.newsapp.activities.NewsActivity
import com.prabhbir07.newsapp.R
import com.prabhbir07.newsapp.viewModel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_article.*


class ArticleFragment : Fragment(R.layout.fragment_article) {
    lateinit var viewModel: NewsViewModel
    val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel

        val article = args.article

        webViewNews.apply { 
            webViewClient = object :WebViewClient(){
                override fun onPageCommitVisible(view: WebView?, url: String?) {
                    super.onPageCommitVisible(view, url)

                    progressBarWebView.visibility = View.GONE
                }
            }
            loadUrl(article.url.toString())
        }

    }
}