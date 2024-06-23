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
import com.elan.mediaserver.android.ui.common.EMSNavController
import com.elan.mediaserver.android.ui.common.GetIcon
import com.elan.mediaserver.android.ui.common.GetSelectedIcon
import com.elan.mediaserver.android.ui.common.NavigationItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun CustomNavigationDrawerItem(
    scope: CoroutineScope,
    drawerState: DrawerState,
    currentSelectedItemId: MutableState<String>,
    navigationItem: NavigationItem
) {
    NavigationDrawerItem(
        icon = {
            if (currentSelectedItemId.value == navigationItem.name) {
                GetSelectedIcon(navigationItem)
            } else {
                GetIcon(navigationItem)
            }
        },
        label = {
            Text(
            text = navigationItem.name,
            modifier = Modifier.padding(8.dp)
        )
        },
        selected = currentSelectedItemId.value == navigationItem.name,
        onClick = {
            currentSelectedItemId.value = navigationItem.name
            scope.launch {
                drawerState.close()
            }
            EMSNavController.navigateTo(navigationItem)
        },
        modifier = Modifier.padding(0.dp, 8.dp, 8.dp, 0.dp),
        shape = RoundedCornerShape(0,100,100,0)
    )
}


@Composable
internal fun RowScope.CustomBottomNavigationItem(
    currentSelectedItemId: MutableState<String>,
    navigationItem: NavigationItem
) {

   NavigationBarItem(
       selected = currentSelectedItemId.value == navigationItem.name,
       onClick = {
           EMSNavController.navigateTo(navigationItem)
           currentSelectedItemId.value = navigationItem.name
       },
       icon = {
           if (currentSelectedItemId.value == navigationItem.name) {
               GetSelectedIcon(navigationItem)
           } else {
               GetIcon(navigationItem)
           }
       },

       colors = NavigationBarItemDefaults.colors(
           indicatorColor = MaterialTheme.colorScheme.inversePrimary,
       )
   )
}

