// FILE: com/wallet/crypto/trustapp/ui/screens/swap/SwapScreen.kt
package com.wallet.crypto.trustapp.ui.screens.swap

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cached
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wallet.crypto.trustapp.ui.screens.home.Token
import com.wallet.crypto.trustapp.ui.screens.home.TrustBlue // Reusing TrustBlue constant

@Composable
fun SwapScreen() {
    // --- Mock Data ---
    val usdcToken = remember {
        Token(1, "Ethereum", "USDC", "", "", 0.0, Icons.Default.Cached, Color(0xFF1E90FF))
    }
    val paxgToken = remember {
        Token(2, "Ethereum", "PAXG", "", "", 0.0, Icons.Default.Cached, Color(0xFFFFCC00))
    }

    // In a real app, this state would be managed by a ViewModel
    var fromToken by remember { mutableStateOf(usdcToken) }
    var toToken by remember { mutableStateOf(paxgToken) }
    var fromAmount by remember { mutableStateOf("0") }
    var toAmount by remember { mutableStateOf("0") }
    // --- End Mock Data ---

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {

        // --- Main Content Area ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {
            // 1. FROM Token Input Card
            TokenInputCard(
                token = fromToken,
                amount = fromAmount,
                isFromToken = true,
                onSelectTokenClick = { /* Navigate to Token Selection */ },
                onBuyClick = { /* Handle Buy */ }
            )

            // 2. Swap Button (Arrows)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                contentAlignment = Alignment.Center
            ) {
                // Swap Icon Button
                Button(
                    onClick = {
                        // Simulate swapping tokens
                        val temp = fromToken
                        fromToken = toToken
                        toToken = temp
                    },
                    modifier = Modifier.size(48.dp),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Icon(
                        Icons.Default.Cached, // Using Cached icon for swap arrows
                        contentDescription = "Swap",
                        tint = TrustBlue,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // 3. TO Token Input Card
            TokenInputCard(
                token = toToken,
                amount = toAmount,
                isFromToken = false,
                onSelectTokenClick = { /* Navigate to Token Selection */ }
            )

            // 4. Conversion Rate (Simulated from screenshot's top right rate)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "1 ${toToken.symbol} = 0.98 ${fromToken.symbol}",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }

        // --- Floating Continue Button ---
        Button(
            onClick = { /* Navigate to confirmation screen */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .align(Alignment.BottomCenter)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = TrustBlue),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Continue", color = Color.White, style = MaterialTheme.typography.titleMedium)
        }
    }
}