package com.example.artcollectionapp.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artcollectionapp.R
import com.example.artcollectionapp.adapter.ResultsAdapter
import com.example.artcollectionapp.adapter.ResultsClickAdapter
import com.example.artcollectionapp.databinding.FragmentDisplayResultsBinding
import com.example.artcollectionapp.model.`object`.Art
import com.example.artcollectionapp.utils.NavigationHelper
import com.example.artcollectionapp.viewModel.ResultState

class DisplayResultsFragment : BaseFragment(), ResultsClickAdapter {

    private val binding by lazy{
        FragmentDisplayResultsBinding.inflate(layoutInflater)
    }

    private val resultsAdapter by lazy{
        ResultsAdapter(this)
    }

    private val recyclerView by lazy{
        binding.resultsRecyclerView
    }

    private var notCalled: Boolean = true



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        recyclerView.apply {
            val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            layoutManager = linearLayoutManager
            adapter = resultsAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    //load more songs when user scrolls to the bottom of recyclerView
                    if(linearLayoutManager.findLastCompletelyVisibleItemPosition()
                    >= linearLayoutManager.itemCount -1){
                        if(notCalled){
                            notCalled = false
                            getArt()
                        }

                    }
                }
            })
        }

        binding.resultsGoBackButton.setOnClickListener {
            artViewModel.currentListInRecycler.clear()
            resultsAdapter.clearList()
            artViewModel.resultsGoBack = false
            artViewModel.resultsFullList.clear()
            if(artViewModel.navigationHelper == NavigationHelper.DEPARTMENT_FRAGMENT){
                findNavController().navigate(R.id.action_DisplayFragment_to_DepartmentFragment)
            }
            if(artViewModel.navigationHelper == NavigationHelper.SEARCH_FRAGMENT){
                findNavController().navigate(R.id.action_DisplayFragment_to_SearchFragment)
            }

        }

        binding.resultsGoBackToMenuButton.setOnClickListener {
            artViewModel.currentListInRecycler.clear()
            resultsAdapter.clearList()
            artViewModel.resultsGoBack = false
            artViewModel.resultsFullList.clear()
            findNavController().navigate(R.id.action_DisplayFragment_to_MainFragment)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        artViewModel.artListLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResultState.LOADING -> {
                    binding.resultsProgressBar.visibility = View.VISIBLE
                }
                is ResultState.SUCCESS<*> -> {
                    binding.resultsProgressBar.visibility = View.GONE
                    Log.d("DisplayResults","Observe Live data before response")
                    val artList = state.response as List<Art>
                    Log.d("DisplayResults", "artList size: " + artList.size.toString())

                    artViewModel.currentListInRecycler.addAll(artList)
                    resultsAdapter.addMore(artList)
                    notCalled = true
                }
                is ResultState.ERROR -> {
                }

            }
        }
        if(artViewModel.resultsGoBack){
            resultsAdapter.addList(artViewModel.currentListInRecycler)
            recyclerView.scrollToPosition(
                artViewModel.currentResultsRecyclerPosition
            )
        }else{
           getArt()
        }

    }

    override fun onStop() {
        super.onStop()

        artViewModel.currentResultsRecyclerPosition =
            (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
        artViewModel.clearArtListLiveData()
    }

    private fun getArt(){
        artViewModel.getObjectsInList()
    }

    override fun onResultsClicked(art: Art) {
        artViewModel.displayResultsArtChoice = art
        findNavController().navigate(R.id.action_DisplayFragment_to_DetailsFragment)
    }

}