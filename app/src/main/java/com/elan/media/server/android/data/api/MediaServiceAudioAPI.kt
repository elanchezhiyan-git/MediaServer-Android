package com.elan.media.server.android.data.api

import com.elan.media.server.android.data.model.AudioDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface MediaServiceAudioAPI {

    @GET("/audio/get")
    suspend fun getAudio(@Query("id") id: String?): AudioDTO

    @GET("audio/stream")
    suspend fun streamAudio(@Query("id") id: String?): AudioDTO

}