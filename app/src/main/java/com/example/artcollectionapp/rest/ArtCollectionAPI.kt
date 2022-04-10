package com.example.artcollectionapp.rest

import com.example.artcollectionapp.model.`object`.Art
import com.example.artcollectionapp.model.department.Department
import com.example.artcollectionapp.model.search.Search
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtCollectionAPI {

    @GET(DEPARTMENTS)
    suspend fun getDepartments() : Response<Department>

    @GET(OBJECT_ID)
    suspend fun getObjectID(
        @Path("objectID") objectID: Int
    ): Response<Art>

    @GET(SEARCH)
    suspend fun searchArtWithDates(
        @Query("hasImages") hasImages: Boolean = false,
        @Query("geoLocation") geoLocation: String? = null,
        @Query("dateBegin") yearBegin: Int? = null,
        @Query("dateEnd") yearEnd: Int? = null,
        @Query("q") searchQuery: String? = null
    ): Response<Search>

    @GET(SEARCH)
    suspend fun searchArtWithoutDates(
        @Query("hasImages") hasImages: Boolean,
        @Query("geoLocation") geoLocation: String? = null,
        @Query("q") searchQuery: String? = null
    ): Response<Search>

    @GET(OBJECTS)
    suspend fun getObjectsByDepartment(
        @Query("departmentIds") departmentId: Int
    ): Response<Search>

    companion object{
        const val BASE_URL = "https://collectionapi.metmuseum.org/public/collection/v1/"

        //https://collectionapi.metmuseum.org/public/collection/v1/objects/[objectID]
        private const val OBJECT_ID = "objects/{objectID}"
        //https://collectionapi.metmuseum.org/public/collection/v1/departments
        private const val DEPARTMENTS = "departments"
        //https://collectionapi.metmuseum.org/public/collection/v1/search
        private const val SEARCH = "search"
        //https://collectionapi.metmuseum.org/public/collection/v1/objects?departmentIds=1
        private const val OBJECTS = "objects"

    }

}