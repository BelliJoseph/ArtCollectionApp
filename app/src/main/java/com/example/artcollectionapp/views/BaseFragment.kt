package com.example.artcollectionapp.views

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.artcollectionapp.repository.ArtRepository
import com.example.artcollectionapp.viewModel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@AndroidEntryPoint
open class BaseFragment : Fragment(){

    val artViewModel: ArtViewModel by viewModels()
}