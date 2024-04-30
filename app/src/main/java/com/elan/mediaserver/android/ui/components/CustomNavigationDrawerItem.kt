package com.elan.mediaserver.android.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.elan.mediaserver.android.ui.common.GetIcon
import com.elan.mediaserver.android.ui.common.GetSelectedIcon
import com.elan.mediaserver.android.ui.common.NavigationMenuItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun CustomNavigationDrawerItem(
    navController: NavController,
    scope: CoroutineScope,
    drawerState: DrawerState,
    currentSelectedItemId: MutableState<String>,
    navigationMenuItem: NavigationMenuItem) {
    NavigationDrawerItem(
        icon = {
            if (currentSelectedItemId.value == navigationMenuItem.name) {
                GetSelectedIcon(navigationMenuItem)
            } else {
                GetIcon(navigationMenuItem)
            }
        },
        label = {
            Text(
            text = navigationMenuItem.name,
            modifier = Modifier.padding(8.dp)
        )
        },
        selected = currentSelectedItemId.value == navigationMenuItem.name,
        onClick = {
            currentSelectedItemId.value = navigationMenuItem.name
            scope.launch {
                drawerState.close()
            }
            navController.navigate(navigationMenuItem.name)
        },
        modifier = Modifier.padding(0.dp, 8.dp, 8.dp, 0.dp),
        shape = RoundedCornerShape(0,100,100,0)
    )
}


@Composable
internal fun RowScope.CustomBottomNavigationItem(
    navController: NavController,
    currentSelectedItemId: MutableState<String>,
    navigationMenuItem: NavigationMenuItem) {

   NavigationBarItem(
       selected = currentSelectedItemId.value == navigationMenuItem.name,
       onClick = {
           navController.navigate(navigationMenuItem.name)
           currentSelectedItemId.value = navigationMenuItem.name
       },
       icon = {
           if (currentSelectedItemId.value == navigationMenuItem.name) {
               GetSelectedIcon(navigationMenuItem)
           } else {
               GetIcon(navigationMenuItem)
           }
       },

       colors = NavigationBarItemDefaults.colors(
           indicatorColor = MaterialTheme.colorScheme.inversePrimary,
       )
   )
}

