package com.elan.media.server.android.ui.views.server

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elan.media.server.android.R

@Composable
fun ServerManager() {
//    Column {
//        Row {
//            Column(modifier = Modifier.height(150.dp).width(150.dp).padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
//                Box (modifier = Modifier.height(88.dp).width(88.dp),contentAlignment = Alignment.Center) {
//                    Image(
//                        painter = painterResource(R.drawable.router_filled),
//                        contentDescription = "Server Icon",
//                        modifier = Modifier.fillMaxSize().align(Alignment.Center)
//                    )
//                }
//                Text("Server Name", fontSize = 12.sp)
//                Text("Server IP", fontSize = 11.sp)
//            }
//
//            Column(modifier = Modifier.height(150.dp).width(150.dp).padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
//                Box (modifier = Modifier.height(88.dp).width(88.dp),contentAlignment = Alignment.Center) {
//                    Image(
//                        painter = painterResource(R.drawable.offline_downloads),
//                        contentDescription = "Server Icon",
//                        modifier = Modifier.fillMaxSize().align(Alignment.Center)
//                    )
//                }
//                Text("Downloads", fontSize = 12.sp)
//            }
//
//        }
//
//        Row {
//            Column(modifier = Modifier.height(150.dp).width(150.dp).padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
//                Box (modifier = Modifier.height(88.dp).width(88.dp),contentAlignment = Alignment.Center) {
//                    Image(
//                        painter = painterResource(R.drawable.add_circle),
//                        contentDescription = "Add Server",
//                        modifier = Modifier.fillMaxSize().align(Alignment.Center)
//                    )
//                }
//                Text("Add Server", fontSize = 12.sp)
//            }
//        }
//    }
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 150.dp), modifier = Modifier.fillMaxSize()) {
        item {
            Column(modifier = Modifier.height(150.dp).width(150.dp).padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Card (modifier = Modifier.height(88.dp).width(88.dp), colors = CardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    disabledContentColor = MaterialTheme.colorScheme.primary
                )
                ) {
                    Image(
                        painter = painterResource(R.drawable.router_filled),
                        contentDescription = "Server Icon",
                        modifier = Modifier.fillMaxSize().align(Alignment.CenterHorizontally)
                    )
                }
                Text("Server Name", fontSize = 12.sp)
                Text("Server IP", fontSize = 11.sp)
            }
        }
        item {
            Column(modifier = Modifier.height(150.dp).width(150.dp).padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Card (modifier = Modifier.height(88.dp).width(88.dp)) {
                    Image(
                        painter = painterResource(R.drawable.add_circle),
                        contentDescription = "Add Server",
                        modifier = Modifier.fillMaxSize().align(Alignment.CenterHorizontally)
                    )
                }
                Text("Add Server", fontSize = 12.sp)
            }
        }

        item {
            Column(modifier = Modifier.height(150.dp).width(150.dp).padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Card (modifier = Modifier.height(88.dp).width(88.dp)) {
                    Image(
                        painter = painterResource(R.drawable.offline_downloads),
                        contentDescription = "Server Icon",
                        modifier = Modifier.fillMaxSize().align(Alignment.CenterHorizontally)
                    )
                }
                Text("Downloads", fontSize = 12.sp)
            }
        }

    }
}

@Preview
@Composable
fun ServerManagerPreview() {
    ServerManager()
}