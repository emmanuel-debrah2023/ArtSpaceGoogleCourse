package com.example.artspacegooglecourse.ui.model


data class ImageData (
    val id: Int,
    val imageId: String,
    val description: String = "",
    val shortDescription: String = "",
    val title: String,
    val completionDate:Int = 0,
    val placeOfOrigin:String,
)
