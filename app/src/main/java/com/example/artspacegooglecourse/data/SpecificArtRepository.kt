package com.example.artspacegooglecourse.data

import com.example.artspacegooglecourse.network.ArtworkApiService

interface SpecificArtRepository{
    suspend fun getArtworkSpecificData()

}
class NetworkSpecificArtRepository(
    private val artworkApiService: ArtworkApiService
) {

}