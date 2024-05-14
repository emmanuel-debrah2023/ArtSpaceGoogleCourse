package com.example.artspacegooglecourse.data

import com.example.artspacegooglecourse.network.ArtworkApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


interface AppContainer {
    val repository: Repository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://api.artic.edu/api/v1/"

    private val intercepter = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder().apply {
        this.addInterceptor(intercepter)
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(25, TimeUnit.SECONDS)
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json {
            ignoreUnknownKeys = true; coerceInputValues = true
        }.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .client(client.build())
        .build()


    private val retrofitService: ArtworkApiService by lazy {
        retrofit.create(ArtworkApiService::class.java)
    }


    override val repository: Repository by lazy {
        NetworkRepository(retrofitService)
    }

}