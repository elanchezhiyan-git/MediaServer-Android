package com.elan.media.server.android.ui.views.music

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elan.media.server.android.data.model.FileDto
import com.elan.media.server.android.data.repository.MediaServiceRepository
import com.elan.media.server.android.permission.RequestAudioPermission
import com.elan.media.server.android.ui.common.EMSNavController
import com.elan.media.server.android.ui.components.musicplayer.rememberExoPlayer
import kotlinx.coroutines.delay

//@Preview
//@Composable
//fun MusicPlayerPreview() {
//    MusicPlayer()
//}
//
//@Composable
//fun MusicPlayer() {
//
//    val file = EMSNavController.retrieveValueById("file") as FileDto?
//    val thumbnailByteArray = EMSNavController.retrieveValueById("thumbnail") as ByteArray?
//    val thumbnail = thumbnailByteArray?.let { BitmapFactory.decodeByteArray(thumbnailByteArray, 0, thumbnailByteArray.size) }
//
//    Column (Modifier.padding(8.dp).fillMaxSize(1f), verticalArrangement = Arrangement.Center) {
//
//        if (thumbnailByteArray == null) {
//            Image(painter = painterResource(R.drawable.music_filled), contentDescription = "Music Symbol",Modifier.size(250.dp).align(Alignment.CenterHorizontally))
//        } else {
//            Image(bitmap = thumbnail!!.asImageBitmap(), contentDescription = "Thumbnail")
//        }
//
//        Text(text = file?.fileName.orEmpty(), fontSize = 24.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
//
//        Row {
//            Button(onClick = { /*TODO*/ },
//                Modifier
//                    .fillMaxWidth(0.5f)
//                    .padding(8.dp)) {
//                Icon(painter = painterResource(R.drawable.play_filled), contentDescription = "Play")
//                Text(text = "Play", modifier = Modifier.padding(4.dp))
//            }
//            Button(onClick = { /*TODO*/ },
//                Modifier
//                    .fillMaxWidth(1f)
//                    .padding(8.dp)) {
//                Icon(painter = painterResource(R.drawable.downloads_filled), contentDescription = "Play")
//                Text(text = "Download", modifier = Modifier.padding(4.dp))
//            }
//        }
//
//        Row (
//            Modifier
//                .fillMaxWidth(1f)
//                .padding(8.dp), Arrangement.SpaceEvenly) {
//            Column ( horizontalAlignment = Alignment.CenterHorizontally) {
//                Icon(painter = painterResource(R.drawable.like_outlined), contentDescription = "Favourites")
//                Text(text = "Like", fontSize = 12.sp)
//            }
//            Column ( horizontalAlignment = Alignment.CenterHorizontally) {
//                Icon(painter = rememberVectorPainter(image = Icons.Filled.Add), contentDescription = "Favourites")
//                Text(text = "My list", fontSize = 12.sp)
//            }
//            Column (horizontalAlignment = Alignment.CenterHorizontally) {
//                Icon(painter = rememberVectorPainter(image = Icons.Outlined.FavoriteBorder), contentDescription = "Favourites")
//                Text(text = "Favourite", fontSize = 12.sp)
//            }
//            Column (horizontalAlignment = Alignment.CenterHorizontally) {
//                Icon(painter = painterResource(R.drawable.options_horizontal_filled), contentDescription = "More")
//                Text(text = "More", fontSize = 12.sp)
//            }
//        }
//
//    }
//
//}

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
    val context = LocalContext.current
    val exoPlayer = rememberExoPlayer(context, audioUrl)
    val isPlaying = remember { mutableStateOf(false) }
    val position = remember { mutableStateOf(0L) }
    val duration = remember { mutableStateOf(1L) } // Avoid division by zero

    // Update progress every 500ms
    LaunchedEffect(exoPlayer) {
        while (true) {
            position.value = exoPlayer.currentPosition
            duration.value = exoPlayer.duration.coerceAtLeast(1L)
            delay(500L)
        }
    }

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

        // Seek Bar
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

    val file = EMSNavController.retrieveValueById("file") as FileDto?
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


