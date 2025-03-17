package com.elan.media.server.android.ui.views.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.elan.media.server.android.data.constants.Category
import com.elan.media.server.android.data.viewmodel.FileViewModel
import com.elan.media.server.android.ui.views.common.CardRow
import com.elan.media.server.shared.annotation.Navigation
import com.elan.media.server.shared.enums.NavigationType

@Navigation(NavigationType.MAIN_MENU,Icons.Outlined.Home, Icons.Filled.Home)
@Composable
fun Home() {

    val viewModel: FileViewModel = viewModel()
    val recentlyAdded by viewModel.recentlyAdded.observeAsState(emptyList())
    val movies by viewModel.movieFiles.observeAsState(emptyList())
    val music by viewModel.musicFiles.observeAsState(emptyList())
    val photos by viewModel.photoFiles.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.getRecentlyAdded()
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
    }
}

@Preview
@Composable
fun HomePreview() {
    Home()
}