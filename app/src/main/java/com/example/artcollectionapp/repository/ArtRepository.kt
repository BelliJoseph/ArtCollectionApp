package com.example.artcollectionapp.repository

import com.example.artcollectionapp.model.`object`.Art
import com.example.artcollectionapp.rest.ArtCollectionAPI
import com.example.artcollectionapp.viewModel.ResultState
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import retrofit2.Response
import java.lang.Exception

interface ArtRepository {
    fun getDepartmentIDs() : Flow<ResultState>
    suspend fun getObjectId(objectId: Int): Response<Art>
    fun getObjectsByDepartment(departmentId: Int): Flow<ResultState>
    fun searchArtWithDates(
        hasImages: Boolean,
        geoLocation: String?,
        yearBegin: Int?,
        yearEnd: Int?,
        searchQuery: String?
    ): Flow<ResultState>
    fun searchArtWithoutDates(
        hasImages: Boolean,
        geoLocation: String?,
        searchQuery: String?
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

    override suspend fun getObjectId(objectId: Int): Response<Art> {
        return artCollectionAPI.getObjectID(objectID = objectId)
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

    override fun searchArtWithDates(
        hasImages: Boolean,
        geoLocation: String?,
        yearBegin: Int?,
        yearEnd: Int?,
        searchQuery: String?
    ): Flow<ResultState> = flow {
        try{
            val response = artCollectionAPI.searchArtWithDates(
                hasImages, geoLocation, yearBegin, yearEnd, searchQuery)
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

    override fun searchArtWithoutDates(
        hasImages: Boolean,
        geoLocation: String?,
        searchQuery: String?
    ): Flow<ResultState> = flow {
        try{
            val response = artCollectionAPI.searchArtWithoutDates(
                hasImages, geoLocation, searchQuery)
            if(response.isSuccessful){
                response.body()?.let {
                    emit(ResultState.SUCCESS(it))
                }?: throw Exception("Search Collection no response")
            }else{
                throw Exception("Search Collection unsuccessful")
            }
        }catch (error: Exception){
            emit(ResultState.ERROR(error))
        }
    }

}