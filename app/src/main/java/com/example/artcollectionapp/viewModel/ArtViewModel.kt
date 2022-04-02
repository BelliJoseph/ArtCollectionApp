package com.example.artcollectionapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artcollectionapp.model.department.DepartmentX
import com.example.artcollectionapp.repository.ArtRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(
    private val artRepository: ArtRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel(){

    var departmentChoice : DepartmentX? = null

    private val _artLiveData : MutableLiveData<ResultState> = MutableLiveData(ResultState.LOADING)
    val artLiveData: LiveData<ResultState> get() = _artLiveData

    fun getDepartmentIDs(){
        _artLiveData.postValue(ResultState.LOADING)

        viewModelScope.launch(coroutineDispatcher) {
            artRepository.getDepartmentIDs()
                .collect{ state ->
                    _artLiveData.postValue(state)
                }
        }
    }

    fun getObjectId(objectId: Int){
        _artLiveData.postValue(ResultState.LOADING)

        viewModelScope.launch(coroutineDispatcher){
            artRepository.getObjectId(objectId)
                .collect{ state ->
                    _artLiveData.postValue(state)
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

    fun searchCollection(
        searchQuery: String? = null,
        hasImages: Boolean? = null,
        geoLocation: String? = null,
        yearBegin: Int? = null,
        yearEnd: Int? = null
    ){
        _artLiveData.postValue(ResultState.LOADING)

        viewModelScope.launch(coroutineDispatcher) {
            artRepository.searchCollection(
                searchQuery, hasImages, geoLocation, yearBegin, yearEnd
            )
                .collect{ state ->
                    _artLiveData.postValue(state)
                }
        }
    }

}