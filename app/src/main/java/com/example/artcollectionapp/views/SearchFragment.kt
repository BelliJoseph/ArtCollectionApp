package com.example.artcollectionapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.artcollectionapp.R
import com.example.artcollectionapp.databinding.FragmentSearchBinding


class SearchFragment : BaseFragment() {

    private val binding by lazy{
        FragmentSearchBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding.searchButton.setOnClickListener {
            findNavController().navigate(R.id.action_SearchFragment_to_DisplayFragment)
        }


        // Inflate the layout for this fragment
        return binding.root
    }


}