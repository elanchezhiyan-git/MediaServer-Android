package com.elan.media.server.android.data.client

import com.elan.media.server.android.data.api.MediaServiceFilesAPI
import com.elan.media.server.android.data.api.MediaServiceMediaAPI
import com.elan.media.server.android.data.api.MediaServiceThumbnailAPI
import com.elan.media.server.android.ui.common.GlobalContext.serverAddress
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MediaServiceClient {

    private var BASE_URL = serverAddress

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val mediaServiceFilesAPI: MediaServiceFilesAPI by lazy {
        retrofit.create(MediaServiceFilesAPI::class.java)
    }

    val mediaServiceThumbnailAPI: MediaServiceThumbnailAPI by lazy {
        retrofit.create(MediaServiceThumbnailAPI::class.java)
    }

    val mediaServiceMediaAPI: MediaServiceMediaAPI by lazy {
        retrofit.create(MediaServiceMediaAPI::class.java)
    }
}