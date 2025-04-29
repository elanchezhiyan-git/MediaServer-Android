package com.elan.media.server.android.ui.components.photoviewer

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.elan.media.server.android.data.model.FileDto
import com.elan.media.server.android.data.repository.MediaServiceRepository
import com.elan.media.server.android.ui.common.EMSNavController
import com.elan.media.server.android.ui.common.GlobalContext
import kotlin.math.abs

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
            .fillMaxSize()
            .fillMaxHeight(1f) // ensures the Box has layout constraints
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
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        var scale by remember { mutableStateOf(1f) }
        var offset by remember { mutableStateOf(Offset.Zero) }
        var imageSize by remember { mutableStateOf(IntSize.Zero) }
        var rotation by remember { mutableStateOf(0f) }
        var accumulatedRotation by remember { mutableStateOf(0f) }
        val boxSize by remember { mutableStateOf(IntSize.Zero) }

        LaunchedEffect(Unit) {
            snapshotFlow { boxSize }
        }


        // Detect tap gestures for zoom in/out
        val tapGestureDetector = Modifier.pointerInput(Unit) {
            detectTapGestures(
                onTap = {
                    GlobalContext.isPhotoViewer = !GlobalContext.isPhotoViewer

                },
                onDoubleTap = {
                    // Tap to zoom in/out functionality
                    scale = (if (scale in 1f..2f) scale + 1f else 1f)
                    offset = if (scale == 1f) Offset.Zero else offset
                }
            )
        }

        // Image with gestures
        Image(
            painter = painter,
            contentDescription = "Image",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(1f)
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offset.x,
                    translationY = offset.y,
                    rotationZ = rotation
                )
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, rotationChange ->
                        val newScale = (scale * zoom).coerceIn(1f, 3f)

                        if (newScale == 1f) {
                            scale = 1f
                            offset = Offset.Zero
                            accumulatedRotation += rotationChange
                            if (abs(accumulatedRotation) > 45f) { // Halfway to 90
                                rotation = if (accumulatedRotation > 0) {
                                    (rotation + 90f) % 360f
                                } else {
                                    (rotation - 90f + 360f) % 360f
                                }
                                accumulatedRotation = 0f
                            }
                        } else {
                            scale = newScale
                            val newOffset = offset + pan* 2F

                            // Calculate bounds
                            val maxX = ((imageSize.width * scale) - boxSize.width) / 2f
                            val maxY = ((imageSize.height * scale) - boxSize.height) / 2f

                            val clampedX = newOffset.x.coerceIn(-maxX, maxX)
                            val clampedY = newOffset.y.coerceIn(-maxY, maxY)

                            offset = Offset(clampedX, clampedY)
                        }
                    }
                }
                .then(tapGestureDetector) // Add tap functionality to the modifier
                .onGloballyPositioned { layoutCoordinates ->
                    imageSize = layoutCoordinates.size
                }
        )

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

@OptIn(ExperimentalLayoutApi::class)
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


@Composable
fun ZoomableImage(
    imageRes: Int,
    modifier: Modifier = Modifier
) {
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    Image(
        painter = painterResource(id = imageRes),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = modifier
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                translationX = offset.x,
                translationY = offset.y
            )
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = (scale * zoom).coerceIn(0.5f, 3f) // Zoom limits
                    offset += pan
                }
            }
    )
}


@Preview
@Composable
fun PhotoViewerPreview() {
    FullscreenImageViewer("http://192.168.1.172:8080/image/stream?id=00120e31-c848-4c22-952a-70294a890b83")
}

