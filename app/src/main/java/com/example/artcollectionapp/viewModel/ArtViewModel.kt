package com.example.artcollectionapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artcollectionapp.model.Validation
import com.example.artcollectionapp.model.`object`.Art
import com.example.artcollectionapp.model.department.Department
import com.example.artcollectionapp.model.search.Search
import com.example.artcollectionapp.repository.ArtRepository
import com.example.artcollectionapp.utils.NavigationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(
    private val artRepository: ArtRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel(){

    //search values
    var imagesOnly: Boolean = false
    var keyWord: String? = null
    var geoLocation: String? = null
    var beginYear: Int? = null
    var endYear: Int? = null

    //results full list
    var resultsFullList: MutableList<Int> = mutableListOf()

    //list of Art currently in DisplayResults recyclerView
    var currentListInRecycler: MutableList<Art> = mutableListOf()

    //currently visible card (top) of DisplayResults recyclerView -> for navigating back
    var currentResultsRecyclerPosition: Int = 0

    //DisplayResults flag to indicate navigation back instead of fresh list
    var resultsGoBack: Boolean = false

    //DisplayResults uses this to know which fragment it waw navigated from -> GoBack button
    var navigationHelper: NavigationHelper = NavigationHelper.NO_FRAGMENT

    //list of 20 Art results to post to the ResultState
    private var resultsToRECYCLER = mutableListOf<Art>()

    //The clicked piece of Art in DisplayResults -> to be used in Details
    var displayResultsArtChoice: Art? = null

    //result list -> while iterating through list = count (current starting point)
    var resultCount: Int = 0
    var currentEnd: Int = 0

    //department list live data -> DepartmentsFragment
    private val _departmentLiveData: MutableLiveData<ResultState> = MutableLiveData(ResultState.LOADING)
    val departmentLiveData: LiveData<ResultState> get() = _departmentLiveData

    //search for art by department number & search query live data -> DepartmentsFragment/ SearchFragment
    private val _artLiveData : MutableLiveData<ResultState> = MutableLiveData(ResultState.LOADING)
    val artLiveData: LiveData<ResultState> get() = _artLiveData

    //display results Art objects live data -> DisplayResultsFragment
    private val _artListLiveData : MutableLiveData<ResultState> = MutableLiveData(ResultState.LOADING)
    val artListLiveData: LiveData<ResultState> get() = _artListLiveData

    fun getDepartmentIDs(){
        _departmentLiveData.postValue(ResultState.LOADING)

        viewModelScope.launch(coroutineDispatcher) {
            artRepository.getDepartmentIDs()
                .collect{ state ->
                    _departmentLiveData.postValue(state)
                }
        }
    }



    fun getObjectsInList(){
        _artListLiveData.postValue(ResultState.LOADING)

        var errorCount: Int = 0

        val tempList = getSubList()
        resultCount = currentEnd
        if(tempList.isEmpty()){
            clearArtListLiveData()
            return
        }

        viewModelScope.launch(coroutineDispatcher){
            for(index in tempList.indices){
                try{
                    val response = artRepository.getObjectId(tempList[index])
                    if(response.isSuccessful){
                        response.body()?.let {
                            resultsToRECYCLER.add(it)
                        } ?: throw Exception("Get ObjectId no response")
                    }else{
                        throw Exception("Get ObjectId unsuccessful")
                    }
                }catch (error: Exception){
//                    _artLiveData.postValue(ResultState.ERROR(error))
                    ++errorCount
                }
            }
            if(errorCount > 0){
                _artListLiveData.postValue(ResultState.ERROR(Exception(errorCount.toString())))
            }
            if(resultsToRECYCLER.isNotEmpty()){
                _artListLiveData.postValue(ResultState.SUCCESS(resultsToRECYCLER.toList()))
            }
        }
    }

    fun getObjectsByDepartment(departmentId: Int){
        _artLiveData.postValue(ResultState.LOADING)

        viewModelScope.launch(coroutineDispatcher){
            artRepository.getObjectsByDepartment(departmentId)
                .collect{ state ->
                    _artLiveData.postValue(state)
                }
        }
    }

    fun searchArtWithDates(
        hasImages: Boolean,
        geoLocation: String? = null,
        yearBegin: Int? = null,
        yearEnd: Int? = null,
        searchQuery: String? = null
    ){
        _artLiveData.postValue(ResultState.LOADING)

        viewModelScope.launch(coroutineDispatcher) {
            artRepository.searchArtWithDates(
                hasImages, geoLocation, yearBegin, yearEnd, searchQuery
            )
                .collect{ state ->
                    _artLiveData.postValue(state)
                }
        }
    }

    fun searchArtWithoutDates(
        hasImages: Boolean,
        geoLocation: String? = null,
        searchQuery: String? = null
    ){
        _artLiveData.postValue(ResultState.LOADING)

        viewModelScope.launch(coroutineDispatcher) {
            artRepository.searchArtWithoutDates(
                hasImages, geoLocation, searchQuery
            )
                .collect{ state ->
                    _artLiveData.postValue(state)
                }
        }
    }


    private fun getSubList(): List<Int>{

        resultsToRECYCLER.clear()

        //add 20 to the current start -> for current end
        currentEnd = resultCount + 20

        if(resultsFullList.size > resultCount) {
            if ( currentEnd > (resultsFullList.size) ) {
                currentEnd = resultsFullList.size
            }

        }else {
            //return an empty list
            return listOf()
        }
        return resultsFullList.toList().subList(resultCount, currentEnd)

    }

    fun userValidation(): Validation{
        var dateEntered: Boolean = false
        var yearFormatted: Boolean = false
        var bothYearsEntered: Boolean = false
        var keywordEntered: Boolean = false

        keyWord?.let {
            keywordEntered = true
        }

        beginYear?.let {
            dateEntered = true
            yearFormatted = (beginYear!! > 0 && beginYear!!.toString().length <= 4)
        }

        endYear?.let {
            dateEntered = true
            yearFormatted = (endYear!! > 0 && endYear!!.toString().length <= 4)
        }

        if(beginYear != null || endYear != null){
            bothYearsEntered = (beginYear != null && endYear != null)
        }

        return Validation(dateEntered, yearFormatted, bothYearsEntered, keywordEntered)
    }

    fun clearDepartmentLiveData() = _departmentLiveData.postValue(ResultState.DEFAULT)

    fun clearArtLiveData() = _artLiveData.postValue(ResultState.DEFAULT)

    fun clearArtListLiveData() = _artListLiveData.postValue(ResultState.DEFAULT)



}