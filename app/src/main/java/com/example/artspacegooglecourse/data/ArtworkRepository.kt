package com.example.artspacegooglecourse.data

import com.example.artspacegooglecourse.network.ArtworkApiService
import com.example.artspacegooglecourse.network.NetworkApiObject
import com.example.artspacegooglecourse.network.NetworkImageData

interface ArtworkRepository {

    suspend fun getArtworkPhotosData(): List<NetworkImageData>

    suspend fun getSpecificArtworkData(id: String): NetworkImageData
}

class NetworkArtworkRepository(
    private val artworkApiService: ArtworkApiService
): ArtworkRepository {

    override suspend fun getArtworkPhotosData(): List<NetworkImageData> {
        val photos: NetworkApiObject = artworkApiService.getPhotos()
        return photos.data
    }

    override suspend fun getSpecificArtworkData(id: String): NetworkImageData {
        val photo = artworkApiService.getPhotoData(id)
        return photo.data[0]
    }
}