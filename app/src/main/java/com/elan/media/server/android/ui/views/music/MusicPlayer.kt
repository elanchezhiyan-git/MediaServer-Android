package com.elan.media.server.android.ui.views.music

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elan.media.server.android.R
import com.elan.media.server.android.data.model.FileDto
import com.elan.media.server.android.data.repository.MediaServiceRepository
import com.elan.media.server.android.permission.RequestAudioPermission
import com.elan.media.server.android.ui.common.EMSNavController
import com.elan.media.server.android.ui.components.musicplayer.rememberExoPlayer
import kotlinx.coroutines.delay

@Composable
fun MusicPlayer(audioUri: Uri, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val exoPlayer = rememberExoPlayer(context, audioUri)
    val isPlaying = remember { mutableStateOf(false) }
    val position = remember { mutableStateOf(0L) }
    val duration = remember { mutableStateOf(1L) } // Avoid division by zero

    // Auto-update progress
    LaunchedEffect(exoPlayer) {
        while (true) {
            position.value = exoPlayer.currentPosition
            duration.value = exoPlayer.duration.coerceAtLeast(1L)
            delay(100L) // Update every 500ms for smoother updates
        }
    }

    // Smooth animation for seek bar
    val animatedProgress by animateFloatAsState(
        targetValue = position.value.toFloat(),
        animationSpec = tween(durationMillis = 300, easing = LinearEasing), label = "seek_animation"

    )

    Column(modifier = modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            if (isPlaying.value) exoPlayer.pause() else exoPlayer.play()
            isPlaying.value = !isPlaying.value
        }) {
            Text(if (isPlaying.value) "Pause" else "Play")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Smooth Seek Bar
        Slider(
            value = animatedProgress,
            onValueChange = { newPosition ->
                exoPlayer.seekTo(newPosition.toLong())
                position.value = newPosition.toLong()
            },
            valueRange = 0f..duration.value.toFloat()
        )

        // Show progress time
        Text("${formatTime(position.value)} / ${formatTime(duration.value)}", fontSize = 14.sp)
    }
}

// Format time (mm:ss)
fun formatTime(timeMs: Long): String {
    val minutes = (timeMs / 1000) / 60
    val seconds = (timeMs / 1000) % 60
    return "%02d:%02d".format(minutes, seconds)
}

@Composable
fun MusicPlayer(audioUrl: String, modifier: Modifier = Modifier) {

    val file = EMSNavController.retrievePreviousStateValueById("file") as FileDto?
    val thumbnailByteArray = EMSNavController.retrievePreviousStateValueById("thumbnail") as ByteArray?
    val thumbnail = thumbnailByteArray?.let { BitmapFactory.decodeByteArray(thumbnailByteArray, 0, thumbnailByteArray.size) }

    val context = LocalContext.current
    val exoPlayer = rememberExoPlayer(context, audioUrl)
    val isPlaying = remember { mutableStateOf(false) }
    val position = remember { mutableLongStateOf(0L) }
    val duration = remember { mutableLongStateOf(1L) } // Avoid division by zero

    // Update progress every 500ms
    LaunchedEffect(exoPlayer) {
        while (true) {
            position.longValue = exoPlayer.currentPosition
            duration.longValue = exoPlayer.duration.coerceAtLeast(1L)
            delay(500L)
        }
    }

    val animatedProgress by animateFloatAsState(
        targetValue = position.longValue.toFloat(),
        animationSpec = tween(durationMillis = 300, easing = LinearEasing), label = "seek_animation"
    )

    Column (Modifier
        .padding(8.dp)
        .fillMaxSize(1f), verticalArrangement = Arrangement.Center) {

        Spacer(modifier = Modifier.height(16.dp))

        if (thumbnailByteArray == null) {
            Image(painter = painterResource(R.drawable.music_filled), contentDescription = "Music Symbol",Modifier
                .size(250.dp)
                .align(Alignment.CenterHorizontally))
        } else {
            Image(bitmap = thumbnail!!.asImageBitmap(), contentDescription = "Thumbnail",Modifier
                .size(250.dp)
                .align(Alignment.CenterHorizontally))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = file?.fileName.orEmpty(), fontSize = 24.sp, modifier = Modifier.align(Alignment.CenterHorizontally))

        Column {

            Spacer(modifier = Modifier.height(16.dp))

            // Seek Bar
            Slider(
                value = animatedProgress,
                onValueChange = { newPosition ->
                    exoPlayer.seekTo(newPosition.toLong())
                    position.longValue = newPosition.toLong()
                },
                valueRange = 0f..duration.longValue.toFloat()
            )

            // Show progress time
            Row(Modifier.fillMaxWidth(1f), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = formatTime(position.longValue), fontSize = 14.sp)
                Text(text = formatTime(duration.longValue), fontSize = 14.sp)
            }

            Row(Modifier.fillMaxWidth(1f)) {

                Button(onClick = {
                    if (!isPlaying.value) {
                        exoPlayer.play()
                        isPlaying.value = true
                    } else {
                        exoPlayer.pause()
                        isPlaying.value = false
                    }
                },
                    Modifier
                        .fillMaxWidth(0.5f)
                        .padding(8.dp)) {
                    Icon(painter = if (!isPlaying.value) painterResource(R.drawable.play_filled) else painterResource(R.drawable.pause_filled), contentDescription = "Download")
                    Text(if (!isPlaying.value) "Play" else "Pause")
                }
                Button(onClick = { /*TODO*/ },
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp)) {
                    Icon(painter = painterResource(R.drawable.downloads_filled), contentDescription = "Download")
                    Text(text = "Download", modifier = Modifier.padding(4.dp))
                }
            }
        }

        Row (
            Modifier
                .fillMaxWidth(1f)
                .padding(8.dp), Arrangement.SpaceEvenly) {
            Column ( horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(painter = painterResource(R.drawable.like_outlined), contentDescription = "Favourites")
                Text(text = "Like", fontSize = 12.sp)
            }
            Column ( horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(painter = rememberVectorPainter(image = Icons.Filled.Add), contentDescription = "Favourites")
                Text(text = "My list", fontSize = 12.sp)
            }
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(painter = rememberVectorPainter(image = Icons.Outlined.FavoriteBorder), contentDescription = "Favourites")
                Text(text = "Favourite", fontSize = 12.sp)
            }
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(painter = painterResource(R.drawable.options_horizontal_filled), contentDescription = "More")
                Text(text = "More", fontSize = 12.sp)
            }
        }

    }


}


@Composable
fun AudioPicker(onAudioSelected: (Uri) -> Unit) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { onAudioSelected(it) }
    }

    Button(onClick = { launcher.launch("audio/*") }) {
        Text("Pick a song")
    }
}

@Composable
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun MusicPlayerScreen() {
    var audioUri by remember { mutableStateOf<Uri?>(null) }

    RequestAudioPermission {
        Column {
            AudioPicker { uri -> audioUri = uri }
            audioUri?.let { MusicPlayer(it) }
        }
    }
}

@Composable
fun MusicPlayerScreen3() {

    val file = EMSNavController.retrievePreviousStateValueById("file") as FileDto?
    var selectedSong by remember { mutableStateOf<String?>(null) }

    // Fetch songs from API
    LaunchedEffect(Unit) {
        try {
            selectedSong = file?.id?.let { MediaServiceRepository().getAudio(it).url.orEmpty() }
        } catch (e: Exception) {
            Log.e("MusicPlayer", "Error fetching songs", e)
        }
    }

    Column {
        selectedSong?.let { MusicPlayer(it) }
    }
}

//@Composable
//fun MusicPlayerMini() {
//    Surface(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable { EMSNavController.navigateTo(NavigationItem.MUSIC_PLAYER) },
//        shadowElevation = 4.dp
//    ) {
//        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
//            Text(song.title, modifier = Modifier.weight(1f))
//            IconButton(onClick = {
//                if (viewModel.isPlaying) viewModel.pause() else viewModel.play(song)
//            }) {
//                Icon(
//                    imageVector = if (viewModel.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
//                    contentDescription = null
//                )
//            }
//        }
//    }
//}
