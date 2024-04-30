package com.elan.mediaserver.android.ui.views.movies

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun Movies() {
    AnimatedVisibility(
        visible = true,
        enter = slideInHorizontally { fullWidth -> fullWidth },
        exit = slideOutHorizontally { fullWidth -> fullWidth }
    ) {
        Column (Modifier.padding(16.dp)) {
//            CardRow(title = "Recently Added") { navController.navigate(com.elan.mediaserver.android.ui.common.SecondaryNavigationItem.MovieDescription.name) }
//            CardRow(title = "Movies") { navController.navigate(com.elan.mediaserver.android.ui.common.SecondaryNavigationItem.MovieDescription.name) }
        }
    }

}