package com.elan.mediaserver.android.data.client

import com.elan.mediaserver.android.data.api.MediaServiceAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object ApiClient {
    val mediaService: MediaServiceAPI by lazy {
        RetrofitClient.retrofit.create(MediaServiceAPI::class.java)
    }
}