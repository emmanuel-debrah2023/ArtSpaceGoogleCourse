package com.example.artspacegooglecourse.network

import com.example.artspacegooglecourse.data.ImageApiModel
//import com.example.artspacegooglecourse.data.Result
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtworkApiService {
    @GET("artworks?limit=10")
    suspend fun getPhotos(): ImageApiModel//Refactor name to end with data

    @GET("artworks")
    suspend fun getPhotoData(
        @Query("ids") id: String,
        @Query("limit") limit : Int = 10
    ): ImageApiModel
}