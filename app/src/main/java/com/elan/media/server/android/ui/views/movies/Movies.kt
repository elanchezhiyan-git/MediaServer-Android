package com.elan.media.server.android.ui.views.movies

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elan.media.server.shared.annotation.Navigation
import com.elan.media.server.shared.enums.NavigationType


class Movies {
    companion object {
        @Navigation(NavigationType.SUB_MENU)
        @Composable
        fun MoviesView() {
            AnimatedVisibility(
                visible = true,
                enter = slideInHorizontally { fullWidth -> fullWidth },
                exit = slideOutHorizontally { fullWidth -> fullWidth }
            ) {
                Column (Modifier.padding(16.dp)) {
                }
            }

        }
    }
}
