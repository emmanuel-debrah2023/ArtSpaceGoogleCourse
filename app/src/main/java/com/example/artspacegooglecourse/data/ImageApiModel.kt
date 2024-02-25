package com.example.artspacegooglecourse.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ImageApiModel(
    val pagination: Pagination,
    val data: List<ImageData>,
    val info: Info,
    val config: Config
)

@Serializable
data class Pagination(
    val total: Int,
    val limit: Int,
    val offset: Int,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("current_page")
    val currentPage: Int,
    @SerialName("next_url")
    val nextUrl: String
)
@Serializable
data class ImageData(
    val id: Int,
    @SerialName(value = "api_model")
    val apiModel: String,
    @SerialName(value = "api_link")
    val apiLink: String,
    @SerialName(value = "is_boosted")
    val isBoosted: Boolean,
    val title: String,
    @SerialName(value = "alt_titles")
    val altTitles: String?,
    @SerialName("image_id")
    val imageId: String = ""
)

@Serializable
data class Info(
    @SerialName("license_text")
    val licenseText: String,
    @SerialName("license_links")
    val licenseLinks: List<String>,
    @SerialName("version")
    val version: String
)

@Serializable
data class Config(
    @SerialName("iiif_url")
    val iiifUrl: String,
    @SerialName("website_url")
    val websiteUrl: String
)
