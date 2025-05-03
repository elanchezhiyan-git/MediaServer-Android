package com.elan.media.server.android.ui.common

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.elan.media.server.android.R
import com.elan.media.server.android.ui.common.NavigationItem.ADD_SERVER
import com.elan.media.server.android.ui.common.NavigationItem.DOWNLOADS
import com.elan.media.server.android.ui.common.NavigationItem.FAVOURITES
import com.elan.media.server.android.ui.common.NavigationItem.HOME
import com.elan.media.server.android.ui.common.NavigationItem.MOVIES
import com.elan.media.server.android.ui.common.NavigationItem.MOVIE_DESCRIPTION
import com.elan.media.server.android.ui.common.NavigationItem.MUSIC
import com.elan.media.server.android.ui.common.NavigationItem.MUSIC_PLAYER
import com.elan.media.server.android.ui.common.NavigationItem.PHOTO_PICKER
import com.elan.media.server.android.ui.common.NavigationItem.PHOTO_VIEWER
import com.elan.media.server.android.ui.common.NavigationItem.SERVER_LOGIN
import com.elan.media.server.android.ui.common.NavigationItem.SERVER_MANAGER
import com.elan.media.server.android.ui.components.photopicker.PhotoPicker
import com.elan.media.server.android.ui.components.photoviewer.PhotoViewer
import com.elan.media.server.android.ui.views.downloads.Downloads
import com.elan.media.server.android.ui.views.favourites.Favourites
import com.elan.media.server.android.ui.views.home.Home
import com.elan.media.server.android.ui.views.movies.MovieDescription
import com.elan.media.server.android.ui.views.movies.Movies
import com.elan.media.server.android.ui.views.music.Music
import com.elan.media.server.android.ui.views.music.MusicPlayerScreen3
import com.elan.media.server.android.ui.views.server.AddServer
import com.elan.media.server.android.ui.views.server.ServerLogin
import com.elan.media.server.android.ui.views.server.ServerManager

enum class NavigationItem(val navigationType: NavigationType) {

    HOME(NavigationType.MAIN_MENU),
    FAVOURITES(NavigationType.MAIN_MENU),
    MOVIES(NavigationType.SUB_MENU),
    MUSIC(NavigationType.SUB_MENU),
    MUSIC_PLAYER(NavigationType.SUB_MENU),
    DOWNLOADS(NavigationType.MAIN_MENU),
    MOVIE_DESCRIPTION(NavigationType.FULL_SCREEN),
    PHOTO_PICKER(NavigationType.POP_OVER),
    PHOTO_VIEWER(NavigationType.FULL_SCREEN),
    SERVER_MANAGER(NavigationType.MAIN_MENU),
    ADD_SERVER(NavigationType.MAIN_MENU),
    SERVER_LOGIN(NavigationType.MAIN_MENU);

    companion object {
        fun getMainMenuItems(): List<NavigationItem> {
            return NavigationItem.entries.filter { NavigationType.MAIN_MENU == it.navigationType }
        }
    }

}


@SuppressLint("NewApi")
@Composable
fun CallNavigationMenuItemComposable(navigationItem: NavigationItem) {
    val function = when (navigationItem) {
        HOME -> Home()
        FAVOURITES -> Favourites()
        MOVIES -> Movies()
        MUSIC -> Music()
        MUSIC_PLAYER -> MusicPlayerScreen3()
        DOWNLOADS -> Downloads()
        MOVIE_DESCRIPTION -> MovieDescription()
        PHOTO_PICKER -> PhotoPicker()
        PHOTO_VIEWER -> PhotoViewer()
        SERVER_LOGIN -> ServerLogin()
        SERVER_MANAGER -> ServerManager()
        ADD_SERVER -> AddServer()
    }
    return function
}

@Composable
fun GetSelectedIcon(navigationItem: NavigationItem): Unit? {

    val function = when (navigationItem) {
        HOME -> Icon(painter = rememberVectorPainter(Icons.Filled.Home), contentDescription = navigationItem.name)
        FAVOURITES -> Icon(painter = rememberVectorPainter(Icons.Filled.Favorite), contentDescription = navigationItem.name)
        MOVIES -> Icon(painter = painterResource(R.drawable.video_filled), contentDescription = navigationItem.name)
        PHOTO_PICKER -> Icon(painter = painterResource(R.drawable.video_filled), contentDescription = navigationItem.name)
        MUSIC -> Icon(painter = painterResource(R.drawable.music_filled), contentDescription = navigationItem.name)
        DOWNLOADS -> Icon(painter = painterResource(R.drawable.downloads_filled), contentDescription = navigationItem.name)
        else -> {
            return null
        }
    }
    return function
}

@Composable
fun GetIcon(navigationItem: NavigationItem): Unit? {

    val function = when (navigationItem) {
        HOME -> Icon(painter = rememberVectorPainter(Icons.Outlined.Home), contentDescription = navigationItem.name)
        FAVOURITES -> Icon(painter = rememberVectorPainter(Icons.Outlined.FavoriteBorder), contentDescription = navigationItem.name)
        MOVIES -> Icon(painter = painterResource(R.drawable.video_outlined), contentDescription = navigationItem.name)
        PHOTO_PICKER -> Icon(painter = painterResource(R.drawable.video_outlined), contentDescription = navigationItem.name)
        MUSIC -> Icon(painter = painterResource(R.drawable.music_outline), contentDescription = navigationItem.name)
        DOWNLOADS -> Icon(painter = painterResource(R.drawable.downloads_filled), contentDescription = navigationItem.name)
        else -> {
            return null
        }
    }
    return function
}

object EMSNavController {

    private lateinit var navigation: (NavigationItem) -> Unit
    private lateinit var popBack: () -> Boolean
    private lateinit var retrievePreviousStateFunction: (String) -> Any?
    private lateinit var retrieveCurrentStateFunction: (String) -> Any?
    private lateinit var storeFunction: (String, Any) -> Any?

    private fun execute(navigationEvent: NavigationItem) {
        navigation.invoke(navigationEvent)
    }

    private fun setNavigation(composable: (NavigationItem) -> Unit) {
        this.navigation = composable;
    }

    private fun setPopBack(popBack: () -> Boolean) {
        this.popBack = popBack
    }

    private fun setRetrieveCurrentStateFunction(retrieveCurrentStateFunction: (String) -> Any?) {
        this.retrieveCurrentStateFunction = retrieveCurrentStateFunction
    }

    private fun setRetrievePreviousStateFunction(retrievePreviousStateFunction: (String) -> Any?) {
        this.retrievePreviousStateFunction = retrievePreviousStateFunction
    }

    private fun setStoreFunction(storeFunction: (String, Any) -> Unit?) {
        this.storeFunction = storeFunction
    }

    fun navigateTo(navigationItem: NavigationItem) {
        Log.d("TAG", "navigateTo: ")
        execute(navigationItem)
    }

    fun popBack() {
        popBack.invoke()
    }

    fun storeMultipleValues(values: Map<String, Any>) {
        for (value in values) {
            storeValueById(value.key, value.value)
        }
    }

    fun retrievePreviousStateMultipleValues(keys: List<String>): Map<String, Any> {
        val values = mutableMapOf<String, Any>()
        for (key in keys) {
            values[key] = retrievePreviousStateValueById(key)!!
        }
        return values
    }

    fun retrieveCurrentStateMultipleValues(keys: List<String>): Map<String, Any> {
        val values = mutableMapOf<String, Any>()
        for (key in keys) {
            values[key] = retrieveCurrentStateValueById(key)!!
        }
        return values
    }

    fun storeValueById(id: String, value: Any) {
        storeFunction.invoke(id, value);
    }

    fun retrievePreviousStateValueById(id: String): Any? {
        return retrievePreviousStateFunction.invoke(id)
    }

    fun retrieveCurrentStateValueById(id: String): Any? {
        return retrieveCurrentStateFunction.invoke(id)
    }

    @Composable
    fun Initialize(navController: NavController) {


        val navigation = remember {
            { navigationItem: NavigationItem ->
                when (navigationItem.navigationType) {
                    NavigationType.MAIN_MENU -> {
                        navController.navigate(navigationItem.name) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                        }
                    }

                    NavigationType.SUB_MENU -> navController.navigate(navigationItem.name)
                    NavigationType.FULL_SCREEN -> navController.navigate(navigationItem.name)
                    NavigationType.POP_OVER -> {
                        navController.navigate(navigationItem.name)
                    }
                    else -> TODO()
                }

            }
        }

        val popBack = remember {
            {
                navController.popBackStack()
            }
        }

        val storeModelById = remember {
            { modelId: String, value: Any ->
                navController.currentBackStackEntry?.savedStateHandle?.set(modelId, value)
            }
        }


        val retrievePreviousModelById = remember {
            { id: String ->
                navController.previousBackStackEntry?.savedStateHandle?.get<Any>(id)
            }
        }

        val retrieveCurrentModelById = remember {
            { id: String ->
                navController.currentBackStackEntry?.savedStateHandle?.get<Any>(id)
            }
        }

        DisposableEffect(Unit) {
            setNavigation(navigation)
            setStoreFunction(storeModelById)
            setRetrievePreviousStateFunction(retrievePreviousModelById)
            setRetrieveCurrentStateFunction(retrieveCurrentModelById)
            setPopBack(popBack)
            onDispose {

            }
        }
    }
}