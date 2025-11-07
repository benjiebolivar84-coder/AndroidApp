// FILE: ./navigation/TrustAppBars.kt
package com.wallet.crypto.trustapp.ui.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * General-purpose Composable for a Center Aligned Top App Bar used by most screens.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrustAppBar(
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable (RowScope.() -> Unit) = {}
) {
    CenterAlignedTopAppBar(
        title = title,
        navigationIcon = navigationIcon,
        actions = actions,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Black
        )
    )
}

/**
 * Provides the configuration (title, visibility, actions) for the TopAppBar based on the current route.
 */
@Composable
fun getAppBarConfig(route: String?): AppBarConfig {
    return when (route) {
        BottomNavItem.Home.route -> AppBarConfig(
            title = "Home",
            showDefaultTopBar = false, // Home uses the custom scrolling header
        )
        BottomNavItem.Trending.route -> AppBarConfig(
            title = "Trending",
            showDefaultTopBar = true,
            actions = {
                IconButton(onClick = {  /* Trending Search */ }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            }
        )
        BottomNavItem.Swap.route -> AppBarConfig(
            title = "Swap",
            showDefaultTopBar = true,
            actions = {
                IconButton(onClick = { /* Swap Settings */ }) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
            }
        )
        BottomNavItem.Earn.route -> AppBarConfig(
            title = "Earn",
            showDefaultTopBar = true,
            actions = {
                IconButton(onClick = { /* Earn Filter */ }) {
                    Icon(Icons.Default.FilterList, contentDescription = "Filter")
                }
            }
        )
        BottomNavItem.Discover.route -> AppBarConfig(
            title = "Discover",
            showDefaultTopBar = true,
            actions = {
                IconButton(onClick = { /* Discover Search */ }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            }
        )
        // Add config for the "settings" screen which is navigated to directly
        "settings" -> AppBarConfig("Settings", true)

        else -> AppBarConfig("", true)
    }
}