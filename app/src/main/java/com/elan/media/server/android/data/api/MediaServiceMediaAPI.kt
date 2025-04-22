package com.elan.media.server.android.data.api

import com.elan.media.server.android.data.model.MediaDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MediaServiceMediaAPI {

    @GET("/audio/get")
    suspend fun getAudio(@Query("id") id: String?): MediaDto

    @GET("audio/stream")
    suspend fun streamAudio(@Query("id") id: String?): MediaDto

    @GET("/video/get")
    suspend fun getVideo(@Query("id") id: String?): MediaDto

    @GET("video/stream")
    suspend fun streamVideo(@Query("id") id: String?): MediaDto

    @GET("/image/get")
    suspend fun getImage(@Query("id") id: String?): MediaDto

}