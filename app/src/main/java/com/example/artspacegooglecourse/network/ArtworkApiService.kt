package com.example.artspacegooglecourse.network

import com.example.artspacegooglecourse.data.ImageApiModel
//import com.example.artspacegooglecourse.data.Result
import retrofit2.http.GET

interface ArtworkApiService {
    @GET("artworks?limit=5")
    suspend fun getPhotos(): ImageApiModel
}