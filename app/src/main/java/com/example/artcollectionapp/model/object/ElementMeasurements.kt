package com.example.artcollectionapp.model.`object`


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ElementMeasurements(
    @Json(name = "depth")
    val depth: Double?,
    @Json(name = "height")
    val height: Double?,
    @Json(name = "width")
    val width: Double?
)