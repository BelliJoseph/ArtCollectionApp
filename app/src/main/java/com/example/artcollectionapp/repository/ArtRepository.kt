package com.example.artcollectionapp.repository

import com.example.artcollectionapp.rest.ArtCollectionAPI
import com.example.artcollectionapp.viewModel.ResultState
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import java.lang.Exception

interface ArtRepository {
    fun getDepartmentIDs() : Flow<ResultState>
    fun getObjectId(objectId: Int): Flow<ResultState>
    fun getObjectsByDepartment(departmentId: Int): Flow<ResultState>
    fun searchCollection(
        searchQuery: String?,
        hasImages: Boolean?,
        geoLocation: String?,
        yearBegin: Int?,
        yearEnd: Int?
    ): Flow<ResultState>


}

class ArtRepositoryImpl @Inject constructor(
    private val artCollectionAPI: ArtCollectionAPI
): ArtRepository{

    override fun getDepartmentIDs(): Flow<ResultState> = flow {
        try{
            val response = artCollectionAPI.getDepartments()
            if(response.isSuccessful){
                response.body()?.let {
                    emit(ResultState.SUCCESS(it))
                } ?: throw Exception("Get Departments no response")
            }else{
                throw Exception("Get Departments unsuccessful")
            }
        }catch (error: Exception){
            emit(ResultState.ERROR(error))
        }
    }

    override fun getObjectId(objectId: Int): Flow<ResultState> = flow {
        try{
            val response = artCollectionAPI.getObjectID(objectId)
            if(response.isSuccessful){
                response.body()?.let {
                    emit(ResultState.SUCCESS(it))
                } ?: throw Exception("Get ObjectId no response")
            }else{
                throw Exception("Get ObjectId unsuccessful")
            }
        }catch (error: Exception){
            emit(ResultState.ERROR(error))
        }
    }

    override fun getObjectsByDepartment(departmentId: Int): Flow<ResultState> = flow {
        try{
            val response = artCollectionAPI.getObjectsByDepartment(departmentId)
            if(response.isSuccessful){
                response.body()?.let {
                    emit(ResultState.SUCCESS(it))
                } ?: throw Exception("Get Objects by Department no response")
            }else{
                throw Exception("Get Objects by Department unsuccessful")
            }
        }catch (error: Exception){
            emit(ResultState.ERROR(error))
        }
    }

    override fun searchCollection(
        searchQuery: String?,
        hasImages: Boolean?,
        geoLocation: String?,
        yearBegin: Int?,
        yearEnd: Int?
    ): Flow<ResultState> = flow {
        try{
            val response = artCollectionAPI.searchCollection(
                searchQuery, hasImages, geoLocation, yearBegin, yearEnd)
            if(response.isSuccessful){
                response.body()?.let {
                    emit(ResultState.SUCCESS(it))
                } ?: throw Exception("Search Collection no response")
            }else{
                throw Exception("Search Collection unsuccessful")
            }
        }catch (error: Exception){
            emit(ResultState.ERROR(error))
        }
    }

}