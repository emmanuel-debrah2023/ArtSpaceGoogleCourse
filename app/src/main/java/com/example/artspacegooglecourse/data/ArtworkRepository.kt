package com.example.artspacegooglecourse.data

import com.example.artspacegooglecourse.network.ArtworkApiService
interface ArtworkRepository {
    suspend fun getArtworkPhotos() : ImageApiModel

    suspend fun getArtworkPhotosData(): List<ImageData>

    suspend fun getSpecificArtworkData(id: String): ImageData
}

class NetworkArtworkRepository(
    private val artworkApiService: ArtworkApiService
): ArtworkRepository {
    override suspend fun getArtworkPhotos(): ImageApiModel = artworkApiService.getPhotos()

    override suspend fun getArtworkPhotosData(): List<ImageData> {
        val photos: ImageApiModel = artworkApiService.getPhotos()
        return photos.data
    }

    override suspend fun getSpecificArtworkData(id: String): ImageData {
        val photo = artworkApiService.getPhotoData(id)
        return photo.data[0]
    }
}