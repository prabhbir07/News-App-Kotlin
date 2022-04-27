package com.prabhbir07.newsapp.fragments

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.View
import com.prabhbir07.newsapp.R
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        linkedin.movementMethod = LinkMovementMethod.getInstance()
        github.movementMethod = LinkMovementMethod.getInstance()

    }




}