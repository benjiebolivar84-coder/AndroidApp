package com.wallet.crypto.trustapp.ui.screens.discover

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

// Data class for the DApp listings
data class DAppItem(
    val id: Int,
    val rank: Int,
    val name: String,
    val description: String,
    val iconColor: Color,
    val icon: ImageVector
)

// Data class for the Latest announcements/news
data class LatestItem(
    val title: String,
    val subtitle: String,
    val iconColor: Color,
    val icon: ImageVector
)