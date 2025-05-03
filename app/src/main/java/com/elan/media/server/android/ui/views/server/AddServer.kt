package com.elan.media.server.android.ui.views.server

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.elan.media.server.android.MainActivity
import com.elan.media.server.android.R
import com.elan.media.server.android.ui.common.GlobalContext

@Composable
fun AddServer() {

    var serverAddress by remember { mutableStateOf("192.168.1.172") }
    var port by remember { mutableStateOf("8100") }
    val context = LocalContext.current


    Column {
        Text(text = "Add Server")

        Spacer(Modifier.padding(16.dp))

        OutlinedTextField(
            label = { Text(text = "Server Address") },
            value = serverAddress,
            onValueChange = { serverAddress = it })

        Spacer(Modifier.padding(16.dp))

        OutlinedTextField(
            label = { Text(text = "Port") },
            value = port,
            onValueChange = { port = it })

        Button(onClick = {
            GlobalContext.serverAddress = "http://$serverAddress:$port"
            context.startActivity(Intent(context, MainActivity::class.java))
            (context as? Activity)?.finish()
        }) {
            Icon(painter = painterResource(R.drawable.add_circle), contentDescription = "Add")
            Text(text = "Add")
        }
    }

}