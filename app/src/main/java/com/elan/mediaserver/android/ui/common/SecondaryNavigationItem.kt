package com.elan.mediaserver.android.ui.common

import androidx.compose.runtime.Composable
import com.elan.mediaserver.android.ui.views.common.MovieDescription

enum class SecondaryNavigationItem() {

    MovieDescription;

    companion object {
        private val homeScreenSecondaryNavigationItems: List<SecondaryNavigationItem> = listOf(MovieDescription)

        fun getHomeScreenSecondaryNavigationItems(): List<SecondaryNavigationItem> {
            return homeScreenSecondaryNavigationItems
        }
    }

}

@Composable
fun CallSecondaryNavigationItemComposable(secondaryNavigationItem: SecondaryNavigationItem) {
    val function = when (secondaryNavigationItem) {
        SecondaryNavigationItem.MovieDescription -> MovieDescription()
    }
    return function
}
