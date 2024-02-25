package com.example.artspacegooglecourse.data

import com.example.artspacegooglecourse.network.ArtworkApiService
interface ArtworkRepository {
    suspend fun getArtworkPhotos() : ImageApiModel

    suspend fun getArtworkPhotosData(): List<ImageData>
}

class NetworkArtworkRepository(
    private val artworkApiService: ArtworkApiService
): ArtworkRepository {
    override suspend fun getArtworkPhotos(): ImageApiModel = artworkApiService.getPhotos()

    override suspend fun getArtworkPhotosData(): List<ImageData> {
        val photos: ImageApiModel = artworkApiService.getPhotos()
        val data = photos.data
        return data
    }
}