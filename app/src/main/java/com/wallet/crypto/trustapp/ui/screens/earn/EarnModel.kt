package com.wallet.crypto.trustapp.ui.screens.earn

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class EarnToken(
    val id: Int,
    val symbol: String,
    val network: String,
    val rate: String,        // e.g., "3.75% APY"
    val iconColor: Color,
    val icon: ImageVector,
    val isAPY: Boolean,      // True for APY, false for APR
    val tags: List<String> = emptyList() // "Bonus", "Over 45K stakers"
)

data class EarnSection(
    val title: String,
    val subtitle: String? = null,
    val tokens: List<EarnToken>
)