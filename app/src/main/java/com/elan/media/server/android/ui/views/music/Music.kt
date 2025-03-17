package com.elan.media.server.android.ui.views.music

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elan.media.server.shared.annotation.Navigation
import com.elan.media.server.shared.enums.NavigationType

//class Music {

//    companion object{
        @Navigation(NavigationType.SUB_MENU,,)
        @Composable
        fun Music() {
            Text(text = "This is Music Screen")
            Spacer(modifier = Modifier.height(16.dp))
        }
//    }
//
//}
