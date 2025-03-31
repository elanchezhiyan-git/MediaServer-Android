package com.elan.media.server.android.ui.views.music

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elan.media.server.android.R
import com.elan.media.server.android.data.model.FileDto
import com.elan.media.server.android.ui.common.EMSNavController

@Preview
@Composable
fun MusicPlayerPreview() {
    MusicPlayer()
}

@Composable
fun MusicPlayer() {

    val file = EMSNavController.retrieveValueById("file") as FileDto?
    val thumbnailByteArray = EMSNavController.retrieveValueById("thumbnail") as ByteArray?
    val thumbnail = thumbnailByteArray?.let { BitmapFactory.decodeByteArray(thumbnailByteArray, 0, thumbnailByteArray.size) }

    Column (Modifier.padding(8.dp).fillMaxSize(1f), verticalArrangement = Arrangement.Center) {

        if (thumbnailByteArray == null) {
            Image(painter = painterResource(R.drawable.music_filled), contentDescription = "Music Symbol",Modifier.size(250.dp).align(Alignment.CenterHorizontally))
        } else {
            Image(bitmap = thumbnail!!.asImageBitmap(), contentDescription = "Thumbnail")
        }

        Text(text = file?.fileName.orEmpty(), fontSize = 24.sp, modifier = Modifier.align(Alignment.CenterHorizontally))

        Row {
            Button(onClick = { /*TODO*/ },
                Modifier
                    .fillMaxWidth(0.5f)
                    .padding(8.dp)) {
                Icon(painter = painterResource(R.drawable.play_filled), contentDescription = "Play")
                Text(text = "Play", modifier = Modifier.padding(4.dp))
            }
            Button(onClick = { /*TODO*/ },
                Modifier
                    .fillMaxWidth(1f)
                    .padding(8.dp)) {
                Icon(painter = painterResource(R.drawable.downloads_filled), contentDescription = "Play")
                Text(text = "Download", modifier = Modifier.padding(4.dp))
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
