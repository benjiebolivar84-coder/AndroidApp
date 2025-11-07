// FILE: ./navigation/BottomNavDestinations.kt
package com.wallet.crypto.trustapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.automirrored.outlined.TrendingUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Defines all primary bottom navigation destinations.
 */
sealed class BottomNavItem(
    val route: String,
    val label: String,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector
) {
    object Home : BottomNavItem("home", "Home", Icons.Filled.Home, Icons.Outlined.Home)
    object Trending : BottomNavItem(
        "trending", "Trending",
        Icons.AutoMirrored.Filled.TrendingUp, Icons.AutoMirrored.Outlined.TrendingUp
    )
    // Note: The original used Filled.Cached for both Swap icons, keeping that for now.
    object Swap : BottomNavItem("swap", "Swap", Icons.Filled.Cached, Icons.Filled.Cached)
    object Earn : BottomNavItem("earn", "Earn", Icons.Filled.AccountBalance, Icons.Outlined.AccountBalance)
    object Discover : BottomNavItem("discover", "Discover", Icons.Filled.Public, Icons.Outlined.Public)
}

/**
 * Helper data class for configuring the app bar per route.
 */
data class AppBarConfig(
    val title: String,
    val showDefaultTopBar: Boolean,
    val actions: (@Composable androidx.compose.foundation.layout.RowScope.() -> Unit)? = null
)