package com.prabhbir07.newsapp.fragments

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.prabhbir07.newsapp.activities.NewsActivity
import com.prabhbir07.newsapp.R
import com.prabhbir07.newsapp.adapters.NewsAdapter
import com.prabhbir07.newsapp.models.Article
import com.prabhbir07.newsapp.util.Resource
import com.prabhbir07.newsapp.viewModel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_breaking_news.*

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    private var package_name = "com.android.chrome"
    val TAG = "BreakingNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()



        newsAdapter.setOnItemClickListener() {
            val bundle=Bundle().apply {
                putSerializable("article",it)
            }

            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )

        }

        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
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
        progressBarBreakingNews.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        progressBarBreakingNews.visibility = View.VISIBLE
    }


    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


    private fun itemClicked(item: Article){




        val builder = CustomTabsIntent.Builder()

        val params = CustomTabColorSchemeParams.Builder()
        //  params.setToolbarColor(ContextCompat.getColor(this@MainActivity, R.color.blue))
        builder.setDefaultColorSchemeParams(params.build())

        builder.setShowTitle(true)

        builder.setShareState(CustomTabsIntent.SHARE_STATE_ON)

        builder.setInstantAppsEnabled(true)

        val customBuilder = builder.build()
        if (context?.isPackageInstalled(package_name) == true) {
            // if chrome is available use chrome custom tabs
            customBuilder.intent.setPackage(package_name)
            context?.let { it1 -> customBuilder.launchUrl(it1, Uri.parse(item.url)) }

        } else {
            // if not available use WebView to launch the url



            Toast.makeText(context,"Web Server Error", Toast.LENGTH_SHORT).show()


        }

    }

    private fun Context.isPackageInstalled(packageName: String): Boolean {
        // check if chrome is installed or not
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }



    }



}