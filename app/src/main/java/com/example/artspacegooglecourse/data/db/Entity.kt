package com.example.artspacegooglecourse.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.artspacegooglecourse.ui.model.ImageData


@Entity(tableName = "images")
data class ImageDataEntity(
    @PrimaryKey(autoGenerate = true)
    val apiId: Int,
    val imageId: String,
    val title: String,
    val description: String,
    val isBoosted: Boolean,
    val apiModel: String,
    val apiLink: String,
    val shortDescription: String,
    val completionDate: Int,
    val placeOfOrigin: String
)

fun ImageDataEntity.uiModelMapper() = ImageData(
    id = apiId,
    imageId = imageId,
    description = description,
    shortDescription = shortDescription,
    title = title,
    completionDate = completionDate,
    placeOfOrigin = placeOfOrigin
)
