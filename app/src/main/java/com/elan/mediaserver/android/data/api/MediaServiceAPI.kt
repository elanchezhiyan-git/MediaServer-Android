package com.elan.mediaserver.android.data.api

import com.elan.mediaserver.android.data.constants.Category
import com.elan.mediaserver.android.data.model.FileDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MediaServiceAPI {

    @GET("/files/getFiles")
    suspend fun getFiles(@Query("id") id: String?, @Header("Category") category: Category?): List<FileDto>

//    @GET("/files/")
//    suspend fun scanFiles(@Query("id") id: String?): List<FileDto>

}