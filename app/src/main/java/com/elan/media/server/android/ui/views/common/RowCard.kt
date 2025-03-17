package com.elan.media.server.android.ui.views.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elan.media.server.android.R
import com.elan.media.server.android.data.model.FileDTOModel
import com.elan.media.server.android.ui.common.EMSNavController
import com.elan.media.server.android.ui.common.NavigationItem

@Composable
fun CardRow(title: String, files: List<FileDTOModel>) {
    Row {
        Text(text = title, fontSize = 20.sp)
    }
    Row (
        Modifier
            .horizontalScroll(rememberScrollState())
            .padding(0.dp, 8.dp)
    ) {
        if (files.isNotEmpty()) {
            for (file in files) {
                Card(file)
            }
        } else{
            for (f in 1..5) {
                val file = FileDTOModel()
                file.fileName = "Sample"
                Card(file)
            }
        }
    }
}


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
//        for (i in 1..10) {
//            Card(file)
//        }
    }
}

@Composable
fun Card(file: FileDTOModel) {
    Column (Modifier.padding(8.dp,4.dp)) {
        androidx.compose.material3.Card(
            Modifier
                .clickable {
                    EMSNavController.storeValueById("id","123")
                    EMSNavController.navigateTo(NavigationItem.MOVIE_DESCRIPTION)
                }) {
            Image(
                painter = painterResource(id = R.drawable.remote),
                contentDescription = "remote",
                modifier = Modifier.widthIn(0.dp, 100.dp)
            )
        }
        Text(modifier = Modifier
            .padding(0.dp, 8.dp)
            .widthIn(0.dp, 100.dp)
            .align(Alignment.CenterHorizontally),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = file.fileName.orEmpty())
    }
}
