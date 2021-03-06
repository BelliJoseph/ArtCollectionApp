package com.example.artcollectionapp.model.`object`


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Art(
    @Json(name = "accessionNumber")
    val accessionNumber: String,
    @Json(name = "accessionYear")
    val accessionYear: String,
    @Json(name = "additionalImages")
    val additionalImages: List<String>,
    @Json(name = "artistAlphaSort")
    val artistAlphaSort: String,
    @Json(name = "artistBeginDate")
    val artistBeginDate: String,
    @Json(name = "artistDisplayBio")
    val artistDisplayBio: String,
    @Json(name = "artistDisplayName")
    val artistDisplayName: String,
    @Json(name = "artistEndDate")
    val artistEndDate: String,
    @Json(name = "artistGender")
    val artistGender: String,
    @Json(name = "artistNationality")
    val artistNationality: String,
    @Json(name = "artistPrefix")
    val artistPrefix: String,
    @Json(name = "artistRole")
    val artistRole: String,
    @Json(name = "artistSuffix")
    val artistSuffix: String,
    @Json(name = "artistULAN_URL")
    val artistULANURL: String,
    @Json(name = "artistWikidata_URL")
    val artistWikidataURL: String,
    @Json(name = "city")
    val city: String,
    @Json(name = "classification")
    val classification: String,
    @Json(name = "constituents")
    val constituents: List<Constituent>?,
    @Json(name = "country")
    val country: String,
    @Json(name = "county")
    val county: String,
    @Json(name = "creditLine")
    val creditLine: String,
    @Json(name = "culture")
    val culture: String,
    @Json(name = "department")
    val department: String,
    @Json(name = "dimensions")
    val dimensions: String,
    @Json(name = "dynasty")
    val dynasty: String,
    @Json(name = "excavation")
    val excavation: String,
    @Json(name = "GalleryNumber")
    val galleryNumber: String,
    @Json(name = "geographyType")
    val geographyType: String,
    @Json(name = "isHighlight")
    val isHighlight: Boolean,
    @Json(name = "isPublicDomain")
    val isPublicDomain: Boolean,
    @Json(name = "isTimelineWork")
    val isTimelineWork: Boolean,
    @Json(name = "linkResource")
    val linkResource: String,
    @Json(name = "locale")
    val locale: String,
    @Json(name = "locus")
    val locus: String,
    @Json(name = "measurements")
    val measurements: List<Measurement>?,
    @Json(name = "medium")
    val medium: String,
    @Json(name = "metadataDate")
    val metadataDate: String,
    @Json(name = "objectBeginDate")
    val objectBeginDate: Int,
    @Json(name = "objectDate")
    val objectDate: String,
    @Json(name = "objectEndDate")
    val objectEndDate: Int,
    @Json(name = "objectID")
    val objectID: Int,
    @Json(name = "objectName")
    val objectName: String,
    @Json(name = "objectURL")
    val objectURL: String,
    @Json(name = "objectWikidata_URL")
    val objectWikidataURL: String,
    @Json(name = "period")
    val period: String,
    @Json(name = "portfolio")
    val portfolio: String,
    @Json(name = "primaryImage")
    val primaryImage: String,
    @Json(name = "primaryImageSmall")
    val primaryImageSmall: String,
    @Json(name = "region")
    val region: String,
    @Json(name = "reign")
    val reign: String,
    @Json(name = "repository")
    val repository: String,
    @Json(name = "rightsAndReproduction")
    val rightsAndReproduction: String,
    @Json(name = "river")
    val river: String,
    @Json(name = "state")
    val state: String,
    @Json(name = "subregion")
    val subregion: String,
    @Json(name = "tags")
    val tags: List<Tag>?,
    @Json(name = "title")
    val title: String
)