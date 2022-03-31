package com.example.artcollectionapp.model.`object`


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Tag(
    @Json(name = "AAT_URL")
    val aATURL: String,
    @Json(name = "term")
    val term: String,
    @Json(name = "Wikidata_URL")
    val wikidataURL: String
)