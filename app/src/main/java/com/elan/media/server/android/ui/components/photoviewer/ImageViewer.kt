package com.elan.media.server.android.ui.components.photoviewer

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.elan.media.server.android.data.model.FileDto
import com.elan.media.server.android.data.repository.MediaServiceRepository
import com.elan.media.server.android.ui.common.EMSNavController


@Composable
fun ImageContainer(imageUrl: String) {
    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        contentScale = ContentScale.Fit
    )

    val state = painter.state

    // This Box fills the entire screen
    Box(
        modifier = Modifier
            .fillMaxSize().fillMaxHeight(1f) // ensures the Box has layout constraints
    ) {
        if (state is AsyncImagePainter.State.Success || state is AsyncImagePainter.State.Loading) {
            Image(
                painter = painter,
                contentDescription = "image",
                modifier = Modifier
                    .fillMaxSize(), // ensures Image fills the Box
                contentScale = ContentScale.Fit // ensures image is shown fully
            )
        }

        when (state) {
            is AsyncImagePainter.State.Loading -> {
                Text("Loading...", modifier = Modifier.align(Alignment.Center), color = Color.White)
            }
            is AsyncImagePainter.State.Error -> {
                Text("Failed to load image", modifier = Modifier.align(Alignment.Center))
            }
            else -> {}
        }
    }
}

@Composable
fun FullscreenImageViewer(imageUrl: String) {
    val painter = rememberAsyncImagePainter(model = imageUrl)
    val state = painter.state

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart
    ) {

//        Zoomable(state = rememberZoomableState()) {
            Image(
                painter = painter,
                contentDescription = "Image",
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.8f),
                contentScale = ContentScale.Fit, // Ensures full image is visible
                alignment = Alignment.Center
            )
//        }

        when (state) {
            is AsyncImagePainter.State.Loading -> {
                Text("Loading...", modifier = Modifier.align(Alignment.Center), color = Color.White)
            }
            is AsyncImagePainter.State.Error -> {
                Text("Failed to load image", modifier = Modifier.align(Alignment.Center))
            }
            else -> {}
        }
    }
}

@Composable
fun PhotoViewer() {
    val file = EMSNavController.retrievePreviousStateValueById("file") as FileDto?
    var selectedImage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            selectedImage = file?.id?.let { MediaServiceRepository().getImage(it).url.orEmpty() }
        } catch (e: Exception) {
            Log.e("Photo Viewer", "Error fetching Image", e)
        }
    }

    Column (Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally)  {
        FullscreenImageViewer(selectedImage.orEmpty())
    }
}

@Preview
@Composable
fun PhotoViewerPreview() {
    FullscreenImageViewer("http://192.168.1.172:8080/image/stream?id=00120e31-c848-4c22-952a-70294a890b83")
}

