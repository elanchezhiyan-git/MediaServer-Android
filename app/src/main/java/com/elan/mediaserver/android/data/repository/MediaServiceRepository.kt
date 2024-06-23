package com.elan.mediaserver.android.data.repository

import com.elan.mediaserver.android.data.client.ApiClient
import com.elan.mediaserver.android.data.model.FileDto

class MediaServiceRepository {
    private val mediaServiceAPI = ApiClient.mediaService;

    suspend fun getFiles() : List<FileDto> {
        return mediaServiceAPI.getFiles(null);
    }
}