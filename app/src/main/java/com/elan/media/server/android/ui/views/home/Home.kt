package com.elan.media.server.android.ui.views.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.elan.media.server.android.data.constants.Category
import com.elan.media.server.android.data.viewmodel.HomeViewModel
import com.elan.media.server.android.ui.views.common.CardRow


@Composable
fun Home() {

    val viewModel: HomeViewModel = viewModel()

    val recentlyAdded by viewModel.recentlyAdded.observeAsState(emptyList())
    val movies by viewModel.movieFiles.observeAsState(emptyList())
    val music by viewModel.musicFiles.observeAsState(emptyList())
    val photos by viewModel.photoFiles.observeAsState(emptyList())

    LaunchedEffect(key1 = true) {
        viewModel.getFiles(Category.RECENTLY_ADDED)
        viewModel.getFiles(Category.MOVIES)
        viewModel.getFiles(Category.MUSIC)
        viewModel.getFiles(Category.PHOTOS)
    }

    Column (Modifier.padding(16.dp)) {
        CardRow(title = "Recently Added", recentlyAdded)
        CardRow(title = "Movies", movies)
        CardRow(title = "Music", music)
        CardRow(title = "Photos", photos)
        CardRow(title = "My Uploads", photos)
    }
}

@Preview
@Composable
fun HomePreview() {
    Home()
}