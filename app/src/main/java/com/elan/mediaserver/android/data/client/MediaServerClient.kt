package com.elan.mediaserver.android.data.client

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


const val BASEURL = "http://localhost:8080/"
data class MediaServerClient(var value: String) {

    companion object{
        private var retrofit:Retrofit?=null
        fun getApiClient(): Retrofit {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(100, TimeUnit.SECONDS)
                .connectTimeout(100, TimeUnit.SECONDS)
                .build()
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASEURL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }

//            val client = OkHttpClient.Builder()
//                .addInterceptor { chain ->
//                    val request: Request = chain.request().newBuilder()
//                        .header("Custom-Header", "Custom-Value")
//                        .build()
//                    chain.proceed(request)
//                }
//                .build()
            return retrofit!!
        }


    }
}