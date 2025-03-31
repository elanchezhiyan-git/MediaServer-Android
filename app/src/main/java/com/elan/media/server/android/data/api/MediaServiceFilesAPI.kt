package com.elan.media.server.android.data.api

import com.elan.media.server.android.data.constants.Category
import com.elan.media.server.android.data.model.FileDto
import com.elan.media.server.android.data.model.UploadResponse
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface MediaServiceFilesAPI {

    @GET("/files/getFiles")
    suspend fun getFiles(@Query("id") id: String?, @Header("Category") category: Category?): List<FileDto>

    @Multipart
    @POST("/files/upload")
    suspend fun uploadFile(@Part file: MultipartBody.Part): UploadResponse


//    @GET("/files/")
//    suspend fun scanFiles(@Query("id") id: String?): List<FileDto>

}