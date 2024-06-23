package com.elan.mediaserver.android.data.api

import com.elan.mediaserver.android.data.model.FileDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MediaServiceAPI {
    @GET("/files/getFiles")
    fun getFiles(@Query("id") id: String?): List<FileDto>
}