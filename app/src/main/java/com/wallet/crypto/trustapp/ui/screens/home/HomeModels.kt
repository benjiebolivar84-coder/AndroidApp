package com.wallet.crypto.trustapp.ui.screens.home

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

// --- Constants ---
val TrustBlue = Color(0xFF0052FF)

// --- Data Models ---

data class Token(
    val id: Int,
    val name: String,
    val symbol: String,
    val price: String,
    val subText: String,
    val changePercent: Double,
    val icon: ImageVector,
    val iconColor: Color,
    val mCapVol: String? = null
)

data class EarnOpportunity(
    val title: String,
    val apy: Double,
    val platform: String,
    val iconColor: Color
)