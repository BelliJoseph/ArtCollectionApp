package com.example.artcollectionapp.views

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.artcollectionapp.R
import com.example.artcollectionapp.databinding.FragmentSearchBinding
import com.example.artcollectionapp.model.search.Search
import com.example.artcollectionapp.viewModel.ResultState


class SearchFragment : BaseFragment() {

    private val binding by lazy{
        FragmentSearchBinding.inflate(layoutInflater)
    }

    private var withDates: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val imageCheckBox = binding.searchImagesCheckBox

        imageCheckBox.setOnClickListener {
            artViewModel.imagesOnly = imageCheckBox.isChecked
        }

        binding.searchButton.setOnClickListener {
            resetAllValues()
            checkAllFields()

            val validation = artViewModel.userValidation()
            if(validation.dateEntered){

                if(validation.yearFormatted){

                    if(validation.bothYearsEntered){
                        withDates = true
                    }else{
                        binding.searchYearFieldsError.visibility = View.VISIBLE
                    }

                }else{
                    binding.searchYearError.visibility = View.VISIBLE
                }
            }

            if(!validation.dateEntered || withDates){
                if(validation.keywordEntered){
                    artViewModel.resultsFullList.clear()
                    getListOfArt()
                }else{
                    binding.searchNoKeywordEnteredError.visibility = View.VISIBLE
                }
            }

        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun getListOfArt(){
        if(withDates){
            artViewModel.searchArtWithDates(
                artViewModel.imagesOnly,
                artViewModel.geoLocation,
                artViewModel.beginYear,
                artViewModel.endYear,
                artViewModel.keyWord)
        }else{
            artViewModel.searchArtWithoutDates(
                artViewModel.imagesOnly,
                artViewModel.geoLocation,
                artViewModel.keyWord)
        }

        artViewModel.artLiveData.observe(viewLifecycleOwner){ state ->
            when(state){
                is ResultState.LOADING ->{
                    binding.searchProgressBar.visibility = View.VISIBLE
                }
                is ResultState.SUCCESS<*> ->{
                    Log.d("getListOfArt", "Success")
                    binding.searchProgressBar.visibility = View.GONE

                    val objectList: Search = state.response as Search
                    Log.d("getListOfArt","objectList size:" + objectList.total.toString())
                    artViewModel.resultsFullList.addAll(objectList.objectIDs)
                    findNavController().navigate(R.id.action_SearchFragment_to_DisplayFragment)
                    Log.d("getListOfArt()", "resultsFullList Total: " + artViewModel.resultsFullList.size.toString())
                }
                is ResultState.ERROR ->{
                    AlertDialog.Builder(requireContext())
                        .setMessage("There was a problem getting the list of Art")
                        .setPositiveButton("Dismiss"){
                                DialogInterface, i->
                            DialogInterface.dismiss() }
                        .create()
                        .show()
                }
            }

        }

    }

    private fun checkAllFields(){
        val keyword = binding.searchKeywordEdit.text
        artViewModel.keyWord = if(keyword.trim().isNotEmpty()){
            val temp = keyword.trim().toString()
            temp.replaceFirstChar { it.uppercase() }
        }else{
            null
        }

        val location = binding.searchOriginEdit.text
        artViewModel.geoLocation = if(location.trim().isNotEmpty()){
            location.trim().toString()
        }else{
            null
        }

        val yearBegin = binding.searchYearBeginEdit.text.toString()
        artViewModel.beginYear = if(yearBegin.trim().isNotEmpty()){
            yearBegin.trim().toInt()

        }else{
            null
        }

        val yearEnd = binding.searchYearEndEdit.text.toString()
        artViewModel.endYear = if(yearEnd.trim().isNotEmpty()){
            yearEnd.trim().toInt()

        }else{
            null
        }
    }

    private fun resetAllValues(){
        withDates = false

        binding.searchNoKeywordEnteredError.visibility = View.GONE
        binding.searchYearError.visibility = View.GONE
        binding.searchYearFieldsError.visibility = View.GONE
    }

}