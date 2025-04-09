package com.elan.media.server.android.data.api

import com.elan.media.server.android.data.model.VideoDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface MediaServiceVideoAPI {

    @GET("/video/get")
    suspend fun getVideo(@Query("id") id: String?): VideoDTO

    @GET("video/stream")
    suspend fun streamVideo(@Query("id") id: String?): VideoDTO

}