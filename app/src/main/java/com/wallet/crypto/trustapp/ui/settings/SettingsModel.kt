package com.wallet.crypto.trustapp.ui.screens.settings

import androidx.compose.ui.graphics.vector.ImageVector

data class SettingsItem(
    val title: String,
    val icon: ImageVector? = null,
    val isToggle: Boolean = false,
    val onClick: () -> Unit
)