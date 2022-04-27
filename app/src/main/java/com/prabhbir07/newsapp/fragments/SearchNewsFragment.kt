package com.prabhbir07.newsapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.prabhbir07.newsapp.activities.NewsActivity
import com.prabhbir07.newsapp.R
import com.prabhbir07.newsapp.adapters.NewsAdapter
import com.prabhbir07.newsapp.util.Constants.Companion.SEARCH_TIME_DELAY
import com.prabhbir07.newsapp.util.Resource
import com.prabhbir07.newsapp.viewModel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    val TAG = "SearchNewsFragment"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }

            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleFragment,
                bundle
            )

        }

        toggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->

            if (isChecked) {
                when (checkedId) {
                    R.id.business -> {
                        val sBusiness = "business"
                        viewModel.searchNews(sBusiness)
                    }
                    R.id.science -> {
                        val sScience = "science"
                        viewModel.searchNews(sScience)
                    }
                    R.id.health -> {
                        val shealth = "health"
                        viewModel.searchNews(shealth)
                    }
                    R.id.sports -> {
                        val sSports = "sports"
                        viewModel.searchNews(sSports)
                    }

                }
            }else{
                if(toggleGroup.checkedButtonId ==View.NO_ID){
                    val sEntertainment = "entertainment"
                    viewModel.searchNews(sEntertainment)
                }
            }
        }


        var job: Job? = null
        etSearchNews.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_TIME_DELAY)

                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.searchNews(editable.toString())

                    }
                }
            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message ")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })


    }

    private fun hideProgressBar() {
        progressBarSearchNews.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        progressBarSearchNews.visibility = View.VISIBLE
    }


    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}