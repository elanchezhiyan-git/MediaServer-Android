package com.elan.mediaserver.android.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import com.elan.mediaserver.android.R
import com.elan.mediaserver.android.ui.views.downloads.Downloads
import com.elan.mediaserver.android.ui.views.favourites.Favourites
import com.elan.mediaserver.android.ui.views.home.Home
import com.elan.mediaserver.android.ui.views.movies.Movies
import com.elan.mediaserver.android.ui.views.music.Music

enum class NavigationMenuItem() {

    Home,
    Favourites,
    Movies,
    Music,
    Downloads;

    companion object {
        private val allItems: List<NavigationMenuItem> = listOf(Home, Favourites, Movies, Music, Downloads)

        fun getBottomNavigationItems(): List<NavigationMenuItem> {
            return allItems
        }
    }

}

@Composable
fun CallNavigationMenuItemComposable(navigationMenuItem: NavigationMenuItem) {
    val function = when (navigationMenuItem) {
        NavigationMenuItem.Home -> Home()
        NavigationMenuItem.Favourites -> Favourites()
        NavigationMenuItem.Movies -> Movies()
        NavigationMenuItem.Music -> Music()
        NavigationMenuItem.Downloads -> Downloads()
    }
    return function
}

@Composable
fun GetSelectedIcon(navigationMenuItem: NavigationMenuItem) {

    val function = when (navigationMenuItem) {
        NavigationMenuItem.Home -> Icon(painter = rememberVectorPainter(Icons.Filled.Home), contentDescription = navigationMenuItem.name)
        NavigationMenuItem.Favourites -> Icon(painter = rememberVectorPainter(Icons.Filled.Favorite), contentDescription = navigationMenuItem.name)
        NavigationMenuItem.Movies -> Icon(painter = painterResource(R.drawable.video_filled), contentDescription = navigationMenuItem.name)
        NavigationMenuItem.Music -> Icon(painter = painterResource(R.drawable.music_filled), contentDescription = navigationMenuItem.name)
        NavigationMenuItem.Downloads -> Icon(painter = painterResource(R.drawable.downloads_filled), contentDescription = navigationMenuItem.name)
    }
    return function
}

@Composable
fun GetIcon(navigationMenuItem: NavigationMenuItem) {

    val function = when (navigationMenuItem) {
        NavigationMenuItem.Home -> Icon(painter = rememberVectorPainter(Icons.Outlined.Home), contentDescription = navigationMenuItem.name)
        NavigationMenuItem.Favourites -> Icon(painter = rememberVectorPainter(Icons.Outlined.FavoriteBorder), contentDescription = navigationMenuItem.name)
        NavigationMenuItem.Movies -> Icon(painter = painterResource(R.drawable.video_outlined), contentDescription = navigationMenuItem.name)
        NavigationMenuItem.Music -> Icon(painter = painterResource(R.drawable.music_outline), contentDescription = navigationMenuItem.name)
        NavigationMenuItem.Downloads -> Icon(painter = painterResource(R.drawable.downloads_filled), contentDescription = navigationMenuItem.name)
    }
    return function
}