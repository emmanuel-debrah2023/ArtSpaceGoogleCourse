package com.example.artspacegooglecourse.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ImagesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(images: ImageDataEntity)

    @Query("SELECT * FROM images")
    suspend fun getImages(): List<ImageDataEntity>

    @Query("SELECT * FROM images WHERE apiId = :id")
    suspend fun getImageById(id: Int): ImageDataEntity

}