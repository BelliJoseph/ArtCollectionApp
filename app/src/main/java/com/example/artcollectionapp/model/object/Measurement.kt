package com.example.artcollectionapp.model.`object`


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Measurement(
    @Json(name = "elementDescription")
    val elementDescription: String?,
    @Json(name = "elementMeasurements")
    val elementMeasurements: ElementMeasurements,
    @Json(name = "elementName")
    val elementName: String
)