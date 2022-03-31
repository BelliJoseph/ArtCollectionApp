package com.example.artcollectionapp.model.department


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DepartmentX(
    @Json(name = "departmentId")
    val departmentId: Int,
    @Json(name = "displayName")
    val displayName: String
)