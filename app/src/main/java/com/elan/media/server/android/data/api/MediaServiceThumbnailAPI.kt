package com.elan.media.server.android.data.api

import com.elan.media.server.android.data.model.ThumbnailDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface MediaServiceThumbnailAPI {

//    @GET("/thumbnail/get")
//    suspend fun getThumbnail(@Path("id") id: String?): ThumbnailDTO


//    @GET("/thumbnail/get")
//    suspend fun getThumbnails(@Query("id") id: List<String>?): List<ThumbnailDTO>
//

    @GET("/thumbnail/get")
    suspend fun getThumbnail(@Query("id") id: String?): ThumbnailDTO


}