package com.example.artcollectionapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.artcollectionapp.R
import com.example.artcollectionapp.databinding.FragmentDetailsBinding


class DetailsFragment : BaseFragment() {

    private val binding by lazy{
        FragmentDetailsBinding.inflate(layoutInflater)
    }

    val artChoice = artViewModel.displayResultsArtChoice

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {




        // Inflate the layout for this fragment
        return binding.root
    }


}