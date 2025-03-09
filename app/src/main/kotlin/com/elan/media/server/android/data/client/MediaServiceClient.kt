package com.elan.media.server.android.data.client

import com.elan.media.server.android.data.api.MediaServiceAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MediaServiceClient {

    private const val BASE_URL = "http://192.168.1.5:8080"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val mediaServiceAPI: MediaServiceAPI by lazy {
        retrofit.create(MediaServiceAPI::class.java)
    }
}