package com.example.artcollectionapp.model.search


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Search(
    @Json(name = "objectIDs")
    val objectIDs: List<Int>,
    @Json(name = "total")
    val total: Int
)