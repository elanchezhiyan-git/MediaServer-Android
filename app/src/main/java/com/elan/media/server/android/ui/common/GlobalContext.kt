package com.elan.media.server.android.ui.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object GlobalContext {

   var serverAddress: String = ""
   var isMusicPlaying: Boolean = false
   var isPhotoViewer by mutableStateOf(true)
   var innerPadding by mutableIntStateOf(0)

}