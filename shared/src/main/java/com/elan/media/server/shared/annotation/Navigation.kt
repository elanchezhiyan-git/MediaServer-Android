package com.elan.media.server.shared.annotation

import com.elan.media.server.shared.enums.NavigationType

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class Navigation(
    val destination: NavigationType,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val selectedIcon: androidx.compose.ui.graphics.vector.ImageVector
)
