package com.example.artcollectionapp.rest

import com.example.artcollectionapp.model.department.Department
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
    )

    @GET(SEARCH)
    suspend fun searchCollection(
        @Query("q") searchQuery: String? = null,
        @Query("hasImages") hasImages: Boolean? = null,
        @Query("geoLocation") geoLocation: String? = null,
        @Query("dateBegin") yearBegin: Int? = null,
        @Query("dateEnd") yearEnd: Int? = null
    )

    @GET(OBJECTS)
    suspend fun getObjectsByDepartment(
        @Query("departmentIds") departmentId: Int
    )

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

    //https://collectionapi.metmuseum.org/public/collection/v1/objects
    //https://collectionapi.metmuseum.org/public/collection/v1/objects?departmentIds=3|9|12
    //https://collectionapi.metmuseum.org/public/collection/v1/objects?metadataDate=2018-10-22
    //https://collectionapi.metmuseum.org/public/collection/v1/objects?metadataDate=2018-10-22&departmentIds=3|9|12
    //https://collectionapi.metmuseum.org/public/collection/v1/search
    //https://collectionapi.metmuseum.org/public/collection/v1/search?q=sunflowers

    //has images query
    //https://collectionapi.metmuseum.org/public/collection/v1/search?hasImages=true&q=Auguste Renoir

    //geolocation and query
    //https://collectionapi.metmuseum.org/public/collection/v1/search?geoLocation=France&q=flowers

    //date range request
    //https://collectionapi.metmuseum.org/public/collection/v1/search?dateBegin=1700&dateEnd=1800&q=African
}