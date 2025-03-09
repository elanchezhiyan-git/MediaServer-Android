package com.elan.media.server.android.ui.common

import android.annotation.SuppressLint
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
import com.elan.media.server.android.ui.common.NavigationItem.DOWNLOADS
import com.elan.media.server.android.ui.common.NavigationItem.FAVOURITES
import com.elan.media.server.android.ui.common.NavigationItem.HOME
import com.elan.media.server.android.ui.common.NavigationItem.MOVIES
import com.elan.media.server.android.ui.common.NavigationItem.MOVIE_DESCRIPTION
import com.elan.media.server.android.ui.common.NavigationItem.MUSIC
import com.elan.media.server.android.ui.common.NavigationItem.PHOTO_PICKER
import com.elan.media.server.android.ui.photopicker.PhotoPicker
import com.elan.media.server.android.ui.views.common.MovieDescription
import com.elan.media.server.android.ui.views.downloads.Downloads
import com.elan.media.server.android.ui.views.favourites.Favourites
import com.elan.media.server.android.ui.views.home.Home
import com.elan.media.server.android.ui.views.movies.Movies
import com.elan.media.server.android.ui.views.music.Music
import com.elan.media.server.shared.enums.NavigationType

enum class NavigationItem(val navigationType: NavigationType) {

    HOME(NavigationType.MAIN_MENU),
    FAVOURITES(NavigationType.MAIN_MENU),
    MOVIES(NavigationType.SUB_MENU),
    MUSIC(NavigationType.SUB_MENU),
    DOWNLOADS(NavigationType.MAIN_MENU),
    MOVIE_DESCRIPTION(NavigationType.FULL_SCREEN),
    PHOTO_PICKER(NavigationType.POP_OVER);

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
        MOVIES -> Movies.MoviesView()
        MUSIC -> Music()
        DOWNLOADS -> Downloads()
        MOVIE_DESCRIPTION -> MovieDescription()
        PHOTO_PICKER -> PhotoPicker()
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

sealed class NavigationEvent() {

    lateinit var navigationItem : NavigationItem
    data object PopBackStack : NavigationEvent()
    data object NavigateTo : NavigationEvent()

}

object EMSNavController {

    private val composables = mutableSetOf<(NavigationEvent) -> Unit>()

    fun execute(navigationEvent: NavigationEvent) {
        composables.forEach { it(navigationEvent) }
    }

    fun addToNavigationStack(composable: (NavigationEvent) -> Unit) {
        composables.add(composable)
    }

    fun removeFromNavigationStack(composable: (NavigationEvent) -> Unit) {
        composables.remove(composable)
    }

    fun navigateTo(navigationItem: NavigationItem) {
        NavigationEvent.NavigateTo.navigationItem = navigationItem
        execute(NavigationEvent.NavigateTo)
    }

    fun popBack() {
        execute(NavigationEvent.PopBackStack)
    }


}

@Composable
fun GlobalNavigationController(navController: NavController) {

    val navigation = remember {
        {
            navigationEvent: NavigationEvent ->
            if (navigationEvent == NavigationEvent.NavigateTo) {
                when (navigationEvent.navigationItem.navigationType) {
                    NavigationType.MAIN_MENU -> {
                        navController.navigate(navigationEvent.navigationItem.name) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                        }
                    }

                    NavigationType.SUB_MENU -> navController.navigate(navigationEvent.navigationItem.name)
                    NavigationType.FULL_SCREEN -> navController.navigate(navigationEvent.navigationItem.name)
                    NavigationType.POP_OVER -> {
                        navController.navigate(navigationEvent.navigationItem.name)
                    }
                    else -> TODO()
                }
            } else if (navigationEvent == NavigationEvent.PopBackStack) {
                navController.popBackStack()
            }

        }
    }

    DisposableEffect(Unit) {
        EMSNavController.addToNavigationStack(navigation)
        onDispose {
            EMSNavController.removeFromNavigationStack(navigation)
        }
    }
}
