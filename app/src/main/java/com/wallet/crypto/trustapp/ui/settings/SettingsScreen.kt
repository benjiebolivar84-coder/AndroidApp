package com.wallet.crypto.trustapp.ui.screens.settings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Headset
import androidx.compose.material.icons.filled.HelpCenter
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    onDebugClick: () -> Unit,
    onToggleTheme: () -> Unit

    ) {
    val functionalItems = listOf(
        SettingsItem(
            title = "Dark Mode",
            icon = Icons.Default.NightsStay,
            isToggle = true,
            onClick = onToggleTheme
        ),
        SettingsItem(title = "Address Book", icon = Icons.Default.Book, onClick = {}),
        SettingsItem(title = "Sync to Extension", icon = Icons.Default.Sync, onClick = {}),
        SettingsItem(title = "Trust handles", icon = Icons.Default.AlternateEmail, onClick = {}),
        SettingsItem(title = "Scan QR code", icon = Icons.Default.QrCodeScanner, onClick = {}),
        SettingsItem(title = "WalletConnect", icon = Icons.Default.Link, onClick = {}),
        SettingsItem(title = "Preferences", icon = Icons.Default.Settings, onClick = {}),
        SettingsItem(title = "Security", icon = Icons.Default.Lock, onClick = {}),
        SettingsItem(title = "Notifications", icon = Icons.Default.Notifications, onClick = {})
    )

    val supportItems = listOf(
        SettingsItem(title = "Help Center", icon = Icons.Default.HelpCenter, onClick = {}),
        SettingsItem(title = "Support", icon = Icons.Default.Headset, onClick = {}),
        SettingsItem(title = "About", icon = Icons.Default.Info, onClick = {}),
        SettingsItem(title = "Debug Tools", icon = Icons.Default.Build, onClick = onDebugClick)

    )

    val socialItems = listOf(
        SettingsItem(title = "X", icon = null, onClick = {}),
        SettingsItem(title = "Telegram", icon = null, onClick = {})
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(top = 8.dp, bottom = 16.dp)
        ) {
            items(functionalItems.size) { index ->
                SettingsItemRow(item = functionalItems[index])
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            items(supportItems.size) { index ->
                SettingsItemRow(item = supportItems[index])
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            items(socialItems.size) { index ->
                SettingsItemRow(item = socialItems[index])
            }
        }
    }
}