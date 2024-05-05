package com.elan.mediaserver.android.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.elan.mediaserver.android.ui.common.NavigationItem
import com.elan.mediaserver.android.ui.views.main.MainView
import kotlinx.coroutines.CoroutineScope

@Composable
fun CustomDrawer(
    drawerState: DrawerState,
    scope: CoroutineScope,
    navController: NavHostController
) {

    val currentSelectedItemId = remember { mutableStateOf(NavigationItem.HOME.name) }
    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels/4

    ModalNavigationDrawer(

        drawerState = drawerState,
        drawerContent = {

            Modifier.fillMaxWidth(0.5f)
            ModalDrawerSheet (modifier = Modifier.widthIn(0.dp, screenWidth.dp)) {

                Text("E - Media Server", modifier = Modifier.padding(16.dp))
                Divider()
                Column (Modifier.padding(0.dp,8.dp)) {
                    CustomNavigationDrawerItem(
                        scope,
                        drawerState,
                        currentSelectedItemId,
                        NavigationItem.HOME
                    )
                    CustomNavigationDrawerItem(
                        scope,
                        drawerState,
                        currentSelectedItemId,
                        NavigationItem.FAVOURITES
                    )
                }
                Divider()
                Column (Modifier.padding(0.dp,16.dp)) {
                    CustomNavigationDrawerItem(
                        scope,
                        drawerState,
                        currentSelectedItemId,
                        NavigationItem.MOVIES
                    )
                    CustomNavigationDrawerItem(
                        scope,
                        drawerState,
                        currentSelectedItemId,
                        NavigationItem.MUSIC
                    )
                    CustomNavigationDrawerItem(
                        scope,
                        drawerState,
                        currentSelectedItemId,
                        NavigationItem.DOWNLOADS
                    )
                }
            }
        },
        content = {
            MainView(scope = scope, drawerState = drawerState, navController = navController, currentSelectedItemId)
        }
    )



}