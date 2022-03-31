package com.example.artcollectionapp.model.`object`


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Constituent(
    @Json(name = "constituentID")
    val constituentID: Int,
    @Json(name = "constituentULAN_URL")
    val constituentULANURL: String,
    @Json(name = "constituentWikidata_URL")
    val constituentWikidataURL: String,
    @Json(name = "gender")
    val gender: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "role")
    val role: String
)