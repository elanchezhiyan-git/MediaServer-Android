package com.elan.media.server.android.data.client

import com.elan.media.server.android.data.api.MediaServiceFilesAPI
import com.elan.media.server.android.data.api.MediaServiceThumbnailAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MediaServiceClient {

    private const val BASE_URL = "http://192.168.1.172:8080"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val mediaServiceFilesAPI: MediaServiceFilesAPI by lazy {
        retrofit.create(MediaServiceFilesAPI::class.java)
    }

    val mediaServiceThumbnailAPI: MediaServiceThumbnailAPI by lazy {
        retrofit.create(MediaServiceThumbnailAPI::class.java)
    }
}