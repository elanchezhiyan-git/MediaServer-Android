package com.elan.mediaserver.android.ui.views.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.elan.mediaserver.android.data.model.FileDto
import com.elan.mediaserver.android.data.viewmodel.FileViewModel
import com.elan.mediaserver.android.ui.views.common.CardRow


@Composable
fun Home(viewModel: FileViewModel) {
    val files by viewModel.files.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchFiles()
    }

    Column (Modifier.padding(16.dp)) {
        CardRow(title = "Recently Added")
        CardRow(title = "Movies")
        CardRow(title = "Series")
        CardRow(title = "Music")
        CardRow(title = "Movies")
        CardRow(title = "Series")
        CardRow(title = "Music")
    }
}

@Preview
@Composable
fun HomePreview() {
    Home()
}