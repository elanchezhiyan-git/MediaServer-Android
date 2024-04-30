package com.elan.mediaserver.android.ui.views.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.elan.mediaserver.android.R
import com.elan.mediaserver.android.ui.common.CallNavigationMenuItemComposable
import com.elan.mediaserver.android.ui.common.NavigationMenuItem
import com.elan.mediaserver.android.ui.components.GetBottomAppBar
import com.elan.mediaserver.android.ui.components.GetTopAppBar
import kotlinx.coroutines.CoroutineScope

@Composable
fun MainView(
    scope: CoroutineScope,
    drawerState: DrawerState,
    navController: NavHostController,
    currentSelectedItemId: MutableState<String>
) {
    Scaffold (
        topBar = { GetTopAppBar(scope, drawerState) },

        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                AnimatedVisibility(
                    visibleState = remember { MutableTransitionState(false) }.apply {
                        targetState = true
                    },
                    modifier = Modifier.fillMaxSize(),
                    enter = slideInHorizontally(
                        initialOffsetX = {  it },
                        animationSpec = tween(durationMillis = 300)
                    ),
                    exit = slideOutHorizontally(
                        targetOffsetX = {  -it },
                        animationSpec = tween(durationMillis = 300)
                    )

                ) {
                    NavHost(navController = navController, startDestination = NavigationMenuItem.Home.name) {
                        for (navigationMenuItem in NavigationMenuItem.entries) {
                            composable(navigationMenuItem.name, content = {
                                CallNavigationMenuItemComposable(navigationMenuItem)
                            })
                        }
                    }
                }
            }

        },

        bottomBar = {
            GetBottomAppBar(navController,currentSelectedItemId)
        },

        floatingActionButton = {
            FloatingActionButton(onClick = {
//                TODO
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