package com.example.artcollectionapp.views

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artcollectionapp.R
import com.example.artcollectionapp.adapter.DepartmentAdapter
import com.example.artcollectionapp.adapter.DepartmentClickAdapter
import com.example.artcollectionapp.databinding.FragmentDepartmentsBinding
import com.example.artcollectionapp.model.department.Department
import com.example.artcollectionapp.model.department.DepartmentX
import com.example.artcollectionapp.model.search.Search
import com.example.artcollectionapp.utils.NavigationHelper
import com.example.artcollectionapp.viewModel.ResultState


class DepartmentsFragment : BaseFragment(), DepartmentClickAdapter {

    private val binding by lazy{
        FragmentDepartmentsBinding.inflate(layoutInflater)
    }

    private val departmentAdapter by lazy{
        DepartmentAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.departmentsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = departmentAdapter
        }

        artViewModel.departmentLiveData.observe(viewLifecycleOwner){ state ->
            when(state){
                is ResultState.LOADING ->{
                    binding.departmentsLoading.visibility = View.VISIBLE
                }
                is ResultState.SUCCESS<*> ->{
                    val departmentList = state.response as Department
                    binding.departmentsLoading.visibility = View.GONE
                    departmentAdapter.addList(departmentList.departments)
                }
                is ResultState.ERROR ->{
                    AlertDialog.Builder(requireContext())
                        .setMessage(state.error.toString())
                        .setPositiveButton("Dismiss"){
                                DialogInterface, i ->
                            DialogInterface.dismiss() }
                        .create()
                        .show()
                }
            }
        }

        artViewModel.artLiveData.observe(viewLifecycleOwner){ state ->
            when(state){
                is ResultState.LOADING ->{
                    binding.departmentsLoading.visibility = View.VISIBLE
                }
                is ResultState.SUCCESS<*> ->{
                    Log.d("onDepartmentClicked", "entered Success")
                    val objectList = state.response as Search
                    binding.departmentsLoading.visibility = View.GONE
                    artViewModel.resultsFullList.clear()
                    artViewModel.resultsFullList.addAll(objectList.objectIDs)
                    artViewModel.navigationHelper = NavigationHelper.DEPARTMENT_FRAGMENT
                    findNavController().navigate(R.id.action_DepartmentFragment_to_DisplayFragment)
                }
                is ResultState.ERROR ->{
                    AlertDialog.Builder(requireContext())
                        .setMessage("There was an Error getting the list of Art")
                        .setPositiveButton("Dismiss"){
                                DialogInterface, i ->
                            DialogInterface.dismiss() }
                        .create()
                        .show()
                }
            }
        }

        artViewModel.getDepartmentIDs()

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDepartmentClicked(departmentx: DepartmentX) {
        Log.d("onDepartmentClicked","entered function")
        artViewModel.getObjectsByDepartment(departmentx.departmentId)
    }

    override fun onStop() {
        super.onStop()
        artViewModel.artLiveData.removeObservers(viewLifecycleOwner)
        artViewModel.clearDepartmentLiveData()
        artViewModel.clearArtLiveData()
    }


}