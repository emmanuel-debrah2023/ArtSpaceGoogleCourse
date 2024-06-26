package com.example.artspacegooglecourse.network

import com.example.artspacegooglecourse.data.db.ImageDataEntity
import com.example.artspacegooglecourse.ui.model.ImageData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class NetworkApiObject(
    @SerialName("data")
    val data: List<NetworkImageData>,
    @SerialName("info")
    val networkInfo: NetworkInfo,
    @SerialName("config")
    val networkConfig: NetworkConfig
)


@Serializable
data class NetworkImageData(
    @SerialName(value = "id")
    val id: Int,
    @SerialName(value = "api_model")
    val apiModel: String,
    @SerialName(value = "api_link")
    val apiLink: String,
    @SerialName(value = "is_boosted")
    val isBoosted: Boolean,
    @SerialName(value = "title")
    val title: String,
    @SerialName("image_id")
    val imageId: String = "",
    @SerialName("description")
    val description: String = "",
    @SerialName("short_description")
    val shortDescription: String = "",
    @SerialName("date_end")
    val completionDate: Int = 0,
    @SerialName("place_of_origin")
    val placeOfOrigin: String = ""
)
fun NetworkImageData.uiModelMapper() = ImageData(
    id = id,
    imageId = imageId,
    title = title,
    description = description,
    shortDescription = shortDescription,
    completionDate = completionDate,
    placeOfOrigin = placeOfOrigin
)

fun  NetworkImageData.entityMapper() = ImageDataEntity(
    apiId = id,
    imageId = imageId,
    title = title,
    description = description,
    shortDescription = shortDescription,
    completionDate = completionDate,
    placeOfOrigin = placeOfOrigin,
    apiModel = apiModel,
    apiLink = apiLink,
    isBoosted = isBoosted
)


@Serializable
data class NetworkInfo(
    @SerialName("license_text")
    val licenseText: String,
    @SerialName("license_links")
    val licenseLinks: List<String>,
    @SerialName("version")
    val version: String
)

@Serializable
data class NetworkConfig(
    @SerialName("iiif_url")
    val iiifUrl: String,
    @SerialName("website_url")
    val websiteUrl: String
)
