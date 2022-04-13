package com.example.artcollectionapp.views

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.artcollectionapp.viewModel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment : Fragment(){

    val artViewModel: ArtViewModel by activityViewModels()
}