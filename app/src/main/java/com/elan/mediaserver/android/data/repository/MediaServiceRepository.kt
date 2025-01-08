package com.elan.mediaserver.android.data.repository

import com.elan.mediaserver.android.data.client.MediaServiceClient
import com.elan.mediaserver.android.data.constants.Category
import com.elan.mediaserver.android.data.model.FileDto

class MediaServiceRepository {

    private val mediaServiceAPI = MediaServiceClient.mediaServiceAPI;

    suspend fun getFiles() : List<FileDto> {
        return mediaServiceAPI.getFiles(" ", null);
    }

    suspend fun getFiles(category: Category) : List<FileDto> {
        return mediaServiceAPI.getFiles(" ", category);
    }

    suspend fun getFiles(id : String,category: Category) : List<FileDto> {
        return mediaServiceAPI.getFiles(id, category);
    }
}