package com.wallet.crypto.trustapp.ui.screens.info

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TrendingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Trending Coins", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))
        Text("1. Bitcoin (BTC) ↑ 3.4%")
        Text("2. Solana (SOL) ↑ 5.8%")
        Text("3. Dogecoin (DOGE) ↑ 2.1%")
        Text("4. Cardano (ADA) ↑ 4.0%")
    }
}
