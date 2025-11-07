package com.wallet.crypto.trustapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wallet.crypto.trustapp.R

data class Coin(val name: String, val symbol: String, val amount: String, val price: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreen() {
    val coins = listOf(
        Coin("Bitcoin", "BTC", "0.142 BTC", "$8,720"),
        Coin("Ethereum", "ETH", "2.5 ETH", "$7,350"),
        Coin("BNB", "BNB", "10 BNB", "$2,300"),
    )

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Wallet", color = Color.White) },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0052FF)),
            actions = {
                IconButton(onClick = { /* QR Action */ }) {
                    Icon(Icons.Default.QrCode, contentDescription = "QR", tint = Color.White)
                }
                IconButton(onClick = { /* Add Token */ }) {
                    Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
                }
            }
        )

        Box(
            modifier = Modifier
                .background(Color(0xFF0052FF))
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Total Balance", color = Color.White.copy(alpha = 0.8f))
                Text("$18,370", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }
        }

        Surface(
            tonalElevation = 4.dp,
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(coins) { coin ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.AccountBalanceWallet, contentDescription = null)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(coin.name, fontWeight = FontWeight.Bold)
                            Text(coin.symbol, color = Color.Gray, fontSize = 13.sp)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(coin.amount, fontWeight = FontWeight.Medium)
                            Text(coin.price, color = Color.Gray, fontSize = 13.sp)
                        }
                    }
                    Divider()
                }
            }
        }
    }
}
