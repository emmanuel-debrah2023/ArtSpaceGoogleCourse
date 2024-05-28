package com.example.artspacegooglecourse.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

interface ImagesDao {
    @Insert
    suspend fun upsert(images: ImageDataEntity)

    @Query("SELECT * FROM images")
    fun getImages(): List<ImageDataEntity>

    @Delete
    suspend fun deleteImage()
}