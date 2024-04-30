package com.elan.mediaserver.android.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.elan.mediaserver.android.ui.common.NavigationMenuItem

@Composable
fun GetBottomAppBar(navController: NavHostController, currentSelectedItemId: MutableState<String>) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
        content = {

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {

                for (bottomNavigationItem in NavigationMenuItem.getBottomNavigationItems()) {
                    CustomBottomNavigationItem(navController, currentSelectedItemId, bottomNavigationItem)
                }
            }
        }
    )
}
