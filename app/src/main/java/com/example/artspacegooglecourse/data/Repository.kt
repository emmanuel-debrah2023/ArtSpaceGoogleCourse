package com.example.artspacegooglecourse.data

import com.example.artspacegooglecourse.network.ArtworkApiService
import com.example.artspacegooglecourse.network.NetworkApiObject
import com.example.artspacegooglecourse.network.NetworkImageData

interface Repository {

    suspend fun getImageDataList(): List<NetworkImageData>

    suspend fun getImageData(id: Int?): NetworkImageData
}

class NetworkRepository(
    private val artworkApiService: ArtworkApiService
): Repository {

    override suspend fun getImageDataList(): List<NetworkImageData> {
        val photos: NetworkApiObject = artworkApiService.getPhotos()
        return photos.data
    }

    override suspend fun getImageData(id: Int?): NetworkImageData {
        val photo = artworkApiService.getPhotoData(id)
        return photo.data[0]
    }
}