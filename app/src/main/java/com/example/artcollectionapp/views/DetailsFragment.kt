package com.example.artcollectionapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.artcollectionapp.R
import com.example.artcollectionapp.databinding.FragmentDetailsBinding
import com.example.artcollectionapp.model.`object`.Art


class DetailsFragment : BaseFragment() {

    private val binding by lazy{
        FragmentDetailsBinding.inflate(layoutInflater)
    }

    private lateinit var artChoice: Art

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        artViewModel.displayResultsArtChoice?.let {
            artChoice = it
        }

        binding.detailsGoBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_DetailsFragment_to_DisplayFragment)
        }

        binding.detailsMenuButton.setOnClickListener {
            artViewModel.currentListInRecycler.clear()
            artViewModel.resultsGoBack = false
            artViewModel.resultsFullList.clear()
            findNavController().navigate(R.id.action_DetailsFragment_to_MainFragment)
        }


        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        artViewModel.resultsGoBack = true

        val text = getString(R.string.detailsTitle, artChoice.title)
        binding.detailsTitle.text = text

        if(artChoice.primaryImage.isEmpty()){
            binding.detailsMainImage.visibility = View.GONE
            binding.detailsImage2.visibility = View.GONE
            binding.detailsImage3.visibility = View.GONE
        }else{
            addPhotoToView(binding.detailsMainImage, artChoice.primaryImage)

            if(artChoice.additionalImages.isNotEmpty()){
                if(artChoice.additionalImages.size >= 2){
                    addPhotoToView(binding.detailsImage2, artChoice.additionalImages[0])

                    addPhotoToView(binding.detailsImage3, artChoice.additionalImages[1])
                }else{
                    addPhotoToView(binding.detailsImage2, artChoice.additionalImages[0])

                    binding.detailsImage3.visibility = View.GONE
                }
            }else{
                binding.detailsImage2.visibility = View.GONE
                binding.detailsImage3.visibility = View.GONE
            }
        }

        if(artChoice.culture.isNotEmpty()){
            binding.detailsCultureEdit.text = artChoice.culture
        }

        if(artChoice.period.isNotEmpty()){
            binding.detailsPeriodEdit.text = artChoice.period
        }

        if(artChoice.artistDisplayName.isNotEmpty()){
            binding.detailsArtistNameEdit.text = artChoice.artistDisplayName
        }

        if(artChoice.artistDisplayBio.isNotEmpty()){
            binding.detailsArtistBioEdit.text = artChoice.artistDisplayBio
        }

        if(artChoice.artistGender.isNotEmpty()){
            binding.detailsArtistGenderEdit.text = artChoice.artistGender
        }

        if(artChoice.medium.isNotEmpty()){
            binding.detailsTypeOfArtEdit.text = artChoice.medium
        }

        if(artChoice.classification.isNotEmpty()){
            binding.detailsClassificationEdit.text = artChoice.classification
        }
    }

    private fun addPhotoToView(view: ImageView, image: String){
        Glide.with(this)
            .load(image)
            .placeholder(R.drawable.ic_baseline_camera_alt_24)
            .error(R.drawable.broken_image)
            .fallback(R.drawable.no_image_plain)
            .into(view)
    }
}