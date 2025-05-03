package com.elan.media.server.android.ui.activities

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.elan.media.server.android.ui.common.CallNavigationMenuItemComposable
import com.elan.media.server.android.ui.common.EMSNavController
import com.elan.media.server.android.ui.common.NavigationItem
import com.elan.media.server.android.ui.theme.MediaServerAndroidTheme

class ServerManagerActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            MediaServerAndroidTheme {

                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

                    EMSNavController.Initialize(navController)

                    NavHost(navController = navController, startDestination = NavigationItem.SERVER_MANAGER.name) {
                        composable(NavigationItem.SERVER_MANAGER.name, content = {
                            CallNavigationMenuItemComposable(NavigationItem.SERVER_MANAGER)
                        })
                        composable(NavigationItem.ADD_SERVER.name, content = {
                            CallNavigationMenuItemComposable(NavigationItem.ADD_SERVER)
                        })
                    }

                }
            }
        }
    }
}