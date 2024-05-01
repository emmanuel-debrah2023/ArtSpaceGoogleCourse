package com.example.artspacegooglecourse.ui.model

import com.example.artspacegooglecourse.network.NetworkImageData


data class ImageData internal constructor(
    val id: Int,
    val imageId: String,
    val description: String,
    val shortDescription: String,
    val title: String,
    val completionDate:Int,
    val placeOfOrigin:String,
){ constructor(networkImageData : NetworkImageData ): this(
    id = networkImageData.id,
    imageId = networkImageData.imageId,
    description = networkImageData.description,
    shortDescription = networkImageData.shortDescription,
    title = networkImageData.title,
    completionDate = networkImageData.completionDate,
    placeOfOrigin = networkImageData.placeOfOrigin
)

}