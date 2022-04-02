package com.example.artcollectionapp.views

import android.os.Bundle
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

    private var proceed: Boolean = true

    private var imagesOnly: Boolean? = null
    private var keyWord: String? = null
    private var geoLocation: String? = null
    private var beginYear: String? = null
    private var endYear: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val imageCheckBox = binding.searchImagesCheckBox

        imageCheckBox.setOnClickListener {
            imagesOnly = imageCheckBox.isChecked
        }

        binding.searchButton.setOnClickListener {
            proceed = true
            keyWord = null
            geoLocation = null
            beginYear = null
            endYear = null
            binding.searchNothingEnteredError.visibility = View.GONE
            binding.searchYearError.visibility = View.GONE
            binding.searchYearFieldsError.visibility = View.GONE

            val keyword = binding.searchKeywordEdit.text
            if(keyword.trim().isNotEmpty()){
                keyWord = keyword.trim().toString()
            }

            val location = binding.searchOriginEdit.text
            if(location.trim().isNotEmpty()){
                geoLocation = location.trim().toString()
            }

            val yearBegin = binding.searchYearBeginEdit.text
            if(yearBegin.trim().isNotEmpty()){
                if(yearBegin.length <= 4){
                    beginYear = yearBegin.trim().toString()
                }else{
                    proceed = false
                    binding.searchYearError.visibility = View.VISIBLE
                }
            }

            val yearEnd = binding.searchYearEndEdit.text
            if(yearEnd.trim().isNotEmpty()){
                if(yearEnd.length <= 4){
                    endYear = yearBegin.trim().toString()
                }else{
                    proceed = false
                    binding.searchYearError.visibility = View.VISIBLE
                }
            }

            if(proceed){
                if(keyWord != null || geoLocation != null){
                    if(beginYear != null && endYear != null){
                        populateFields()
                        findNavController().navigate(R.id.action_SearchFragment_to_DisplayFragment)
                    }else if(beginYear != null || endYear != null){
                        binding.searchYearFieldsError.visibility = View.VISIBLE
                    }else{
                        populateFields()
                        findNavController().navigate(R.id.action_SearchFragment_to_DisplayFragment)
                    }

                }else{
                    binding.searchNothingEnteredError.visibility = View.VISIBLE
                }
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun populateFields(){
        artViewModel.imageOnly = imagesOnly
        artViewModel.keyWord = keyWord
        artViewModel.location = geoLocation
        artViewModel.yearStart = beginYear
        artViewModel.yearEnd = endYear
    }

}