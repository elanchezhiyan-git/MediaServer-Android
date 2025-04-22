package com.elan.media.server.android.data.repository

import android.util.Log
import com.elan.media.server.android.data.client.MediaServiceClient
import com.elan.media.server.android.data.constants.Category
import com.elan.media.server.android.data.model.MediaDto
import com.elan.media.server.android.data.model.FileDto
import com.elan.media.server.android.data.model.ThumbnailDTO
import com.elan.media.server.android.data.model.UploadResponse
import okhttp3.MultipartBody

class MediaServiceRepository {

    private val mediaServiceFilesAPI = MediaServiceClient.mediaServiceFilesAPI

    private val mediaServiceThumbnailAPI = MediaServiceClient.mediaServiceThumbnailAPI

    private val mediaServiceMediaAPI = MediaServiceClient.mediaServiceMediaAPI

    suspend fun getFiles() : List<FileDto> {
        return mediaServiceFilesAPI.getFiles(" ", null);
    }

    suspend fun getFiles(category: Category) : List<FileDto> {
        return mediaServiceFilesAPI.getFiles(" ", category);
    }

    suspend fun getFiles(id : String,category: Category) : List<FileDto> {
        return mediaServiceFilesAPI.getFiles(id, category);
    }

    suspend fun uploadFiles(part: MultipartBody.Part) : UploadResponse {
        return mediaServiceFilesAPI.uploadFile(part)
    }

    suspend fun getThumbnail(id : String) : ThumbnailDTO {
        val thumbnail = mediaServiceThumbnailAPI.getThumbnail(id)
        Log.d("API Response", thumbnail.toString())
        return thumbnail
    }

    suspend fun getAudio(id: String) : MediaDto {
        val mediaDto = mediaServiceMediaAPI.getAudio(id)
        Log.d("API Response", mediaDto.url.orEmpty())
        return mediaDto
    }

    suspend fun getVideo(id: String) : MediaDto {
        val mediaDto = mediaServiceMediaAPI.getVideo(id)
        Log.d("API Response", mediaDto.url.orEmpty())
        return mediaDto
    }

    suspend fun getImage(id: String) : MediaDto {
        val mediaDto = mediaServiceMediaAPI.getImage(id)
        Log.d("API Response", mediaDto.url.orEmpty())
        return mediaDto
    }

}