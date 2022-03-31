package com.example.artcollectionapp.model.department


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Department(
    @Json(name = "departments")
    val departments: List<DepartmentX>
)