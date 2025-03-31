package com.elan.media.server.android.ui.views.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import com.elan.media.server.android.ui.common.NavigationItem
import com.elan.media.server.android.ui.common.NavigationItem.PHOTO_PICKER
import com.elan.media.server.android.ui.common.NavigationType
import com.elan.media.server.android.ui.components.GetSubMenuTopAppBar
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
    Scaffold (
        topBar = { if (showNavigationBar) GetTopAppBar(scope, drawerState) else GetSubMenuTopAppBar() },

        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                navController.addOnDestinationChangedListener{ _,destination,_ ->
                    for (it in NavigationItem.entries) {
                        if (destination.route == it.name) {
                            showNavigationBar = when (it.navigationType) {
                                NavigationType.MAIN_MENU -> true
                                NavigationType.FULL_SCREEN -> false
                                NavigationType.SUB_MENU -> true
                                NavigationType.POP_OVER -> false
                            }
                            if (it.navigationType == NavigationType.MAIN_MENU) {
                                currentSelectedItemId.value = it.name
                            }
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
            FloatingActionButton(onClick = {
                EMSNavController.navigateTo(PHOTO_PICKER)
            }) {
                Icon(
                    painter = painterResource(R.drawable.upload_filled),
                    contentDescription = "Play Icon",
                    tint = LocalContentColor.current
                )
            }
        }
    )
}