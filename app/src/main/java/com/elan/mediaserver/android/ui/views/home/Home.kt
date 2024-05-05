package com.elan.mediaserver.android.ui.views.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elan.mediaserver.android.ui.views.common.CardRow


@Composable
fun Home() {
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