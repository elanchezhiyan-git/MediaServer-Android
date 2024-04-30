package com.elan.mediaserver.android.ui.views.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elan.mediaserver.android.R

@Composable
fun CardRow(title: String) {
    Row {
        Text(text = title, fontSize = 20.sp)
    }
    Row (
        Modifier
            .horizontalScroll(rememberScrollState())
            .padding(0.dp, 8.dp)
    ) {
        var i = 0;
        while (i < 10) {
            Card()
            i++
        }
    }
}

@Composable
fun Card() {
    Column (Modifier.padding(8.dp,4.dp)) {
        androidx.compose.material3.Card {
            Image(
                painter = painterResource(id = R.drawable.remote),
                contentDescription = "remote",
                modifier = Modifier.widthIn(0.dp, 150.dp)
            )
        }
        Text(modifier = Modifier
            .padding(0.dp, 8.dp)
            .fillMaxWidth(1f)
            .align(Alignment.CenterHorizontally), text = "Movies")
    }
}


@Composable
fun MovieDescription() {
    Column (Modifier.padding(8.dp)) {
        Image(painter = painterResource(id = R.drawable.remote__1_), contentDescription = "movie banner")
        Row {
            Text(text = "Premalu", modifier = Modifier.padding(8.dp))
            Text(text = "2024", modifier = Modifier.padding(8.dp))
            Text(text = "Romance", modifier = Modifier.padding(8.dp))
        }

        Row {
            Text(text = "Video:", modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Bold)
            Text(text = "1080p H264", modifier = Modifier.padding(0.dp,8.dp))

            Text(text = "Audio:", modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Bold)
            Text(text = "Tamil EAC3 5.1 (Default)", modifier = Modifier.padding(0.dp,8.dp))
        }

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
                .padding(8.dp),Arrangement.SpaceEvenly) {
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
                Icon(painter = painterResource(R.drawable.delete_outlined), contentDescription = "Delete")
                Text(text = "Delete", fontSize = 12.sp)
            }
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(painter = painterResource(R.drawable.options_horizontal_filled), contentDescription = "More")
                Text(text = "More", fontSize = 12.sp)
            }
        }

        Row (Modifier.padding(0.dp,16.dp)) {
            Text(text = "Sachin pursues romance but finds himself caught betwwen two potential partners" +
                    ", leading to amusing complications")
        }

        CardRow(title = "Cast & Crew")
    }
}

@Preview
@Composable
fun MovieDescriptionPreview() {
    MovieDescription()
}