package com.elan.media.server.android.ui.views.video

import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.elan.media.server.android.data.model.FileDto
import com.elan.media.server.android.data.repository.MediaServiceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(file: FileDto?, modifier: Modifier = Modifier) {

    val context = LocalContext.current
    var url by remember { mutableStateOf<String?>(null) }


    LaunchedEffect(Unit) {
        try {
            url = file?.id?.let { MediaServiceRepository().getVideo(it).url }
        } catch (e: Exception) {
            Log.e("MusicPlayer", "Error fetching songs", e)
        }
    }

    url?.let {
        val exoPlayer = remember(url) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.Builder().setUri(url).setMimeType(file?.contentType).build())
            prepare()
            playWhenReady = false
        } }

        DisposableEffect(exoPlayer) {
            onDispose {
                CoroutineScope(Dispatchers.Main).launch {
                    delay(200) // let UI settle before releasing
                    exoPlayer.release()
                }
            }
        }

        AndroidView(
            factory = {
                PlayerView(context).apply {
                    this.player = exoPlayer
                    useController = true
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT

                    // Enable fullscreen button
                    setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS)
                    setControllerShowTimeoutMs(0) // Always show controls
                }
            },
            modifier = modifier
        )
    }


}
