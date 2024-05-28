package com.example.artspacegooglecourse.data

import androidx.room.Entity


@Entity(tableName = "images")
data class ImageDataEntity(
    val id: Int,
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