package com.elan.media.server.android.ui.photopicker

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.net.toFile
import com.elan.media.server.android.data.repository.MediaServiceRepository
import com.elan.media.server.android.ui.common.EMSNavController
import com.google.common.io.Files
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@RequiresExtension(extension = Build.VERSION_CODES.R, version = 2)
@Composable
fun PhotoPicker() {
    val context = LocalContext.current;
    val photoPickerLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.PickMultipleVisualMedia(MediaStore.getPickImagesMaxLimit())) {
            if (it.isEmpty()) {
                EMSNavController.popBack()
            } else {
                it.forEach { uri ->
                    val parcelFileDescriptor = context.contentResolver.openFileDescriptor(uri, "r")
                    parcelFileDescriptor?.use { pfd ->
                        val inputStream = ParcelFileDescriptor.AutoCloseInputStream(pfd)
                        val fileNameFromUri = getFileNameFromUri(context, uri)?:""
                        val file = File(context.cacheDir,fileNameFromUri)
                        file.outputStream().use { output ->
                            inputStream.copyTo(output)
                        }

                        val requestFile = file.asRequestBody(context.contentResolver.getType(uri)?.toMediaTypeOrNull())
                        val imagePart =
                            MultipartBody.Part.createFormData("file", file.name, requestFile)
                        val mediaServiceRepository = MediaServiceRepository()

                        GlobalScope.launch {
                            val response = mediaServiceRepository.uploadFiles(imagePart)
                            if (response.success) {
                                // Image upload successful, handle the response
//                                val imageUrl = response.body()?.imageUrl
                                // Do something with imageUrl
                            } else {
                                // Image upload failed, handle the error
//                                val errorMessage = response.body()?.message
                                // Handle the error
                            }
                            file.delete()
                        }
                    }

                    parcelFileDescriptor?.close()
                }
                EMSNavController.popBack()
            }
        }
    LaunchedEffect(Unit) {
        photoPickerLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo),
        )
    }

}

fun getTypeFromUri(context: Context, uri: Uri): String {
    return context.contentResolver.getType(uri)?.substringAfterLast("/") ?: "tmp"
}

fun getFileNameFromUri(context: Context, uri: Uri): String? {
    val cursor = context.contentResolver.query(uri, null, null, null, null) ?: return null
    return cursor.use {
        if (it.moveToFirst()) {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (nameIndex != -1) it.getString(nameIndex) else "temp"
        } else "temp"
    }
}

@Preview
@Composable
@RequiresExtension(extension = Build.VERSION_CODES.R, version = 2)
fun PhotoPickerRoutePreview() {
    PhotoPicker()
}