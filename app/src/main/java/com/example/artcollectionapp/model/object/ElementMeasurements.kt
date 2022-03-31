package com.example.artcollectionapp.model.`object`


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ElementMeasurements(
    @Json(name = "Depth")
    val depth: Double,
    @Json(name = "Height")
    val height: Double,
    @Json(name = "Width")
    val width: Double
)