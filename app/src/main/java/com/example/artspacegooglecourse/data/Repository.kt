package com.example.artspacegooglecourse.data

import android.util.Log
import com.example.artspacegooglecourse.data.db.AppDatabase
import com.example.artspacegooglecourse.data.db.ImageDataEntity
import com.example.artspacegooglecourse.network.ArtworkApiService
import com.example.artspacegooglecourse.network.NetworkApiObject
import com.example.artspacegooglecourse.network.NetworkImageData
import com.example.artspacegooglecourse.network.entityMapper

interface Repository {

    suspend fun getImagesDataList(): List<NetworkImageData>

    suspend fun fetchImageDataList()

    suspend fun getImageData(id: Int?): NetworkImageData

    suspend fun fetchImageData(id: Int?)

    suspend fun getSavedImagesDataList(): List<ImageDataEntity>

    suspend fun getSavedImageById(id: Int): ImageDataEntity

    suspend fun upsert(image: ImageDataEntity)

}

class NetworkRepository(
    private val artworkApiService: ArtworkApiService,
    private val db: AppDatabase
) : Repository {

    override suspend fun fetchImageDataList() {
        try {
            val photos: NetworkApiObject = artworkApiService.getPhotos()
            photos.data.map { it.entityMapper() }
                .map { db.getImageDao().upsert(it) }
        } catch (exception: Exception) {
            throw exception
        }
    }

    override suspend fun fetchImageData(id: Int?) {
        try {
            val photo = artworkApiService.getPhotoData(id)
            db.getImageDao().upsert(photo.data[0].entityMapper())
        } catch (exception: Exception) {
            throw exception
        }
    }

    override suspend fun getImageData(id: Int?): NetworkImageData {
        val photo = artworkApiService.getPhotoData(id)
        return photo.data[0]
    }

    override suspend fun getImagesDataList(): List<NetworkImageData> {
        val photoList = artworkApiService.getPhotos()
        return photoList.data
    }

    override suspend fun getSavedImageById(id: Int): ImageDataEntity {
        val image: ImageDataEntity = db.getImageDao().getImageById(id)
        return image
    }


    override suspend fun getSavedImagesDataList(): List<ImageDataEntity> {
        var list: List<ImageDataEntity>
        Log.d("Repository", "DB call for all Images ")
        list = db.getImageDao().getImages()
        Log.d("Repository", "SELECT all returns: $list")
        if (list.isEmpty()) {
            list = artworkApiService.getPhotos().data.map { it.entityMapper() }
        }
        return list
    }

    override suspend fun upsert(image: ImageDataEntity) = db.getImageDao().upsert(image)
}