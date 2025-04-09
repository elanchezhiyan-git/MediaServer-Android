package com.elan.media.server.android.ui.components.musicplayer

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.elan.media.server.android.ui.common.GlobalContext

@Composable
fun rememberExoPlayer(context: Context, url: Uri): ExoPlayer {
    val player = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(url))
            prepare()
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            GlobalContext.isMusicPlaying = true
        }
    }
    return player
}

@Composable
fun rememberExoPlayer(context: Context, url: String): ExoPlayer {
    val player = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(url))
            prepare()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            GlobalContext.isMusicPlaying = true
        }
    }
    return player
}
