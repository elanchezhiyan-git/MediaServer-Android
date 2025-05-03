package com.elan.media.server.android.ui.views.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.elan.media.server.android.R
import com.elan.media.server.android.ui.common.CallNavigationMenuItemComposable
import com.elan.media.server.android.ui.common.EMSNavController
import com.elan.media.server.android.ui.common.GlobalContext
import com.elan.media.server.android.ui.common.NavigationItem
import com.elan.media.server.android.ui.common.NavigationType
import com.elan.media.server.android.ui.components.GetSubMenuTopAppBar
import com.elan.media.server.android.ui.components.GetSubMenuTransparentTopAppBar
import com.elan.media.server.android.ui.components.GetTopAppBar
import kotlinx.coroutines.CoroutineScope

@Composable
fun MainView(
    scope: CoroutineScope,
    drawerState: DrawerState,
    navController: NavHostController,
    currentSelectedItemId: MutableState<String>
) {
    var showNavigationBar by remember { mutableStateOf(true) }
    var showSubMenuNavigationBar by remember { mutableStateOf(true) }
    var showPlayer by remember { mutableStateOf(false) }
    var isPhotoViewer by remember { mutableStateOf(false) }
    var globalTopBar = GlobalContext.isPhotoViewer
    var isUpload by remember { mutableStateOf(false) }

    Scaffold (
        topBar = { if (showSubMenuNavigationBar)  GetSubMenuTopAppBar() else if (globalTopBar && isPhotoViewer) GetSubMenuTransparentTopAppBar() else if (showNavigationBar) GetTopAppBar(scope, drawerState) },

        content = {
            Column(
                modifier = Modifier.fillMaxSize()
                    .then(
                        if (!isPhotoViewer) Modifier.verticalScroll(rememberScrollState())
                        else Modifier
                    )
            ) {
                navController.addOnDestinationChangedListener{ _,destination,_ ->

                    if (currentSelectedItemId.value == NavigationItem.MUSIC_PLAYER.name) {
                       showPlayer = GlobalContext.isMusicPlaying
                    }

                    for (it in NavigationItem.entries) {
                        if (destination.route == it.name) {

                            showNavigationBar = it.navigationType == NavigationType.SUB_MENU || it.navigationType == NavigationType.MAIN_MENU

                            showSubMenuNavigationBar = it.navigationType == NavigationType.SUB_MENU

                            if (it.navigationType == NavigationType.MAIN_MENU) {
                                currentSelectedItemId.value = it.name
                            }

                            isUpload = (it.navigationType == NavigationType.SUB_MENU
                                    || it.navigationType == NavigationType.MAIN_MENU)

                            isPhotoViewer = it == NavigationItem.PHOTO_VIEWER

                        }
                    }
                }

                EMSNavController.Initialize(navController)

                NavHost(navController = navController, startDestination = NavigationItem.HOME.name) {
                    for (navigationMenuItem in NavigationItem.entries) {
                        composable(navigationMenuItem.name, content = {
                            CallNavigationMenuItemComposable(navigationMenuItem)
                        })
                    }
                }
            }

        },

        floatingActionButton = {
            if (!isPhotoViewer) {
                FloatingActionButton(onClick = {
                    EMSNavController.navigateTo(NavigationItem.PHOTO_PICKER)
                }) {
                    Icon(
                        painter = painterResource(R.drawable.upload_filled),
                        contentDescription = "Play Icon",
                        tint = LocalContentColor.current
                    )
                }
            }
        },

//        bottomBar = {
//            if (showPlayer) {
//                MusicPlayerMini()
//            }
//

    )
}
