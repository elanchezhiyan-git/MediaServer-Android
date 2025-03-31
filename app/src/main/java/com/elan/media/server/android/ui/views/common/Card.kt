package com.elan.media.server.android.ui.views.common

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.elan.media.server.android.R
import com.elan.media.server.android.data.constants.Category
import com.elan.media.server.android.data.model.FileDto
import com.elan.media.server.android.data.viewmodel.ThumbnailViewModel
import com.elan.media.server.android.ui.common.EMSNavController
import com.elan.media.server.android.ui.common.NavigationItem


@Composable
fun Card(file: FileDto?, viewModel: ThumbnailViewModel) {
    val thumbnail = viewModel.thumbnails[file?.thumbnail]

    val bitmap = produceState<Bitmap?>(initialValue = null, thumbnail) {
        value = thumbnail?.data?.let {
            val byteArray = Base64.decode(it, Base64.DEFAULT) // If Base64 encoded
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }
    }

    LaunchedEffect(file?.thumbnail) {
        file?.thumbnail?.let { viewModel.getThumbnail(it) }
    }

    Column(Modifier.padding(8.dp, 4.dp)) {

        val category = file?.category

        androidx.compose.material3.Card(
            Modifier.clickable {
                when (category) {
                    Category.MOVIES -> EMSNavController.navigateTo(NavigationItem.MOVIE_DESCRIPTION)
                    Category.MUSIC -> {
                        EMSNavController.storeValueById("file", file)
                        thumbnail?.data?.let { Base64.decode(it, Base64.DEFAULT) }
                            ?.let { EMSNavController.storeValueById("thumbnail", it) }
                        EMSNavController.navigateTo(NavigationItem.MUSIC_PLAYER)
                    }
                    else -> EMSNavController.navigateTo(NavigationItem.MOVIE_DESCRIPTION)
                }
            }) {
            if (bitmap.value == null) {
                when (category) {
                    Category.RECENTLY_ADDED -> Image(
                        painter = painterResource(R.drawable.photo_filled),
                        contentDescription = "Play Icon", Modifier
                            .height(100.dp)
                            .width(200.dp)
                    )
                    Category.MOVIES -> Image(
                        painter = painterResource(R.drawable.video_filled),
                        contentDescription = "Play Icon", Modifier
                            .height(100.dp)
                            .width(200.dp)
                    )
                    Category.MUSIC -> Image(
                        painter = painterResource(R.drawable.music_filled),
                        contentDescription = "Play Icon", Modifier
                            .height(100.dp)
                            .width(200.dp)
                    )
                    Category.PHOTOS -> Image(
                        painter = painterResource(R.drawable.photo_filled),
                        contentDescription = "Play Icon", Modifier
                            .height(100.dp)
                            .width(200.dp)
                    )
                    else -> Image(
                        painter = painterResource(R.drawable.photo_filled),
                        contentDescription = "Play Icon", Modifier
                            .height(100.dp)
                            .width(200.dp)
                    )
                }
            } else {
                Image(
                    bitmap = bitmap.value!!.asImageBitmap(),
                    contentDescription = "Loaded Image",
                    modifier = Modifier
                        .width(200.dp)
                        .height(100.dp)
                        .fillMaxSize()
                )
            }
        }

        Text(
            modifier = Modifier
                .padding(0.dp, 8.dp)
                .widthIn(0.dp, 100.dp)
                .align(Alignment.CenterHorizontally),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = file?.fileName.orEmpty()
        )
    }
}

