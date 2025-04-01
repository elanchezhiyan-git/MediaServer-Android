package com.elan.media.server.android.permission

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@Composable
@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun RequestAudioPermission(onPermissionGranted: @Composable () -> Unit) {
    val permissionState = rememberPermissionState(Manifest.permission.READ_MEDIA_AUDIO)

    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }

    if (permissionState.status.isGranted) {
        onPermissionGranted()
    } else {
        Text("Permission required to access music files.")
    }
}
