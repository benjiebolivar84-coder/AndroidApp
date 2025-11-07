package com.wallet.crypto.trustapp.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val TrustTextDark = Color.Black

@Composable
fun HomeAction(icon: ImageVector, text: String, isPrimary: Boolean = false) {
    val buttonColor = if (isPrimary) TrustBlue else Color.White
    val iconTint = if (isPrimary) Color.White else TrustTextDark
    val containerShape = CircleShape

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(IntrinsicSize.Max)
    ) {
        Card(
            onClick = { },
            modifier = Modifier.size(56.dp),
            shape = containerShape,
            colors = CardDefaults.cardColors(containerColor = buttonColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = text,
                    tint = iconTint,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = TrustTextDark
        )
    }
}

@Composable
fun HomeActionsRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        HomeAction(icon = Icons.Default.CallMade, text = "Send")
        HomeAction(icon = Icons.Default.Add, text = "Fund", isPrimary = true)
        HomeAction(icon = Icons.Default.SwapHoriz, text = "Swap")
        HomeAction(icon = Icons.Default.AccountBalance, text = "Sell")
        HomeAction(icon = Icons.Default.Spa, text = "Earn")
    }
}

@Composable
fun HomeHeader(
    totalBalance: String,
    changePercent: String,
    onSettingsClick: () -> Unit,
    onQrCodeScannerClick: () -> Unit // ADDED QR SCANNER PARAMETER
) {
    val growthColor = if (changePercent.startsWith("-")) Color(0xFFFF1744) else Color(0xFF00C853)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Top Row: Settings/Scanner on Left, Search on Right
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // LEFT SIDE: Settings and QR Scanner
            Row {
                IconButton(onClick = onSettingsClick) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings", tint = TrustTextDark)
                }
                // QR Scanner icon exact left side after setting icon
                IconButton(onClick = onQrCodeScannerClick) {
                    Icon(
                        imageVector = Icons.Default.QrCodeScanner,
                        contentDescription = "Scan QR Code",
                        tint = TrustTextDark
                    )
                }
            }

            // RIGHT SIDE: Search
            IconButton(onClick = { /* Search/Filter */ }) {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = TrustTextDark)
            }
        }

        // Center Balance (Below the icons)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = totalBalance,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = TrustTextDark
            )
            Text(
                text = changePercent,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = growthColor
            )
        }
    }
}