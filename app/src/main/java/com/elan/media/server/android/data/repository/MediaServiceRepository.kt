package com.elan.media.server.android.data.repository

import com.elan.media.server.android.data.client.MediaServiceClient
import com.elan.media.server.android.data.constants.Category
import com.elan.media.server.android.data.model.FileDTOModel
import com.elan.media.server.android.data.model.UploadResponse
import okhttp3.MultipartBody

class MediaServiceRepository {

    private val mediaServiceAPI = MediaServiceClient.mediaServiceAPI

    suspend fun getFiles() : List<FileDTOModel> {
        return mediaServiceAPI.getFiles(" ", null)
    }

    suspend fun getFiles(category: Category) : List<FileDTOModel> {
        return mediaServiceAPI.getFiles(" ", category)
    }

    suspend fun getFiles(id : String,category: Category) : List<FileDTOModel> {
        return mediaServiceAPI.getFiles(id, category)
    }

    suspend fun uploadFiles(part: MultipartBody.Part) : UploadResponse {
        return mediaServiceAPI.uploadFile(part)
    }
}