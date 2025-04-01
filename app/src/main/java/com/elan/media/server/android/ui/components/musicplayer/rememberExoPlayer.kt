package com.elan.media.server.android.ui.components.musicplayer

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

@Composable
fun rememberExoPlayer(context: Context, audioUri: Uri): ExoPlayer {
    return remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(audioUri))
            prepare()
        }
    }
}

@Composable
fun rememberExoPlayer(context: Context, audioUrl: String): ExoPlayer {
    return remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(audioUrl))
            prepare()
        }
    }
}
