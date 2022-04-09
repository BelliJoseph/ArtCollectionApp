package com.example.artcollectionapp.model

data class Validation(
    val dateEntered: Boolean,
    val yearFormatted: Boolean,
    val bothYearsEntered: Boolean,
    val keywordEntered: Boolean
)
