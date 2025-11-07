package com.wallet.crypto.trustapp.ui.screens.trending

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wallet.crypto.trustapp.ui.screens.home.Token
import com.wallet.crypto.trustapp.ui.screens.home.TrustBlue // REUSE TrustBlue constant

// Placeholder for reusable components (REUSE from your existing files)
@Composable
fun TopNavBar(title: String, onSettingsClick: () -> Unit, onSearchClick: () -> Unit) {
    // This is where you would reuse your HomeHeader/CompactHomeHeader structure or a standard TopAppBar
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        modifier = Modifier.padding(16.dp)
    )
    Divider()
}

// Placeholder for reusable components (REUSE from your existing files)

@Composable
fun TrendingScreen(
    onTokenClick: (Token) -> Unit = {}
) {
    // --- Mock Data based on Screenshot ---
    val trendingTokens = remember {
        listOf(
            Token(1, "Aster", "AST", "$1.18", "MCap: $2.3B • Vol: $62B", 2.77, Icons.Default.Circle, Color(0xFFF06292)),
            Token(2, "ChainOpera AI", "COAI", "$10.40", "MCap: $1.9B • Vol: $134B", -27.64, Icons.Default.Circle, Color(0xFF00ACC1)),
            Token(3, "Pump.fun", "PUMP", "$0", "MCap: $1.4B • Vol: $305B", 13.01, Icons.Default.Circle, Color(0xFF4CAF50)),
            Token(4, "PAX Gold", "PAXG", "$4,244", "MCap: $1.3B • Vol: $145B", 0.02, Icons.Default.Circle, Color(0xFFFFEB3B)),
            Token(5, "OFFICIAL TRUMP", "TRUMP", "$5.89", "MCap: $1.1B • Vol: $187B", 1.46, Icons.Default.Circle, Color(0xFFF44336)),
            Token(6, "Tether Gold", "XAUT", "$4,239", "MCap: $1.0B • Vol: $154B", -0.10, Icons.Default.Circle, Color(0xFFF9A825)),
            Token(7, "PancakeSwap Token", "CAKE", "$2.87", "MCap: $985M • Vol: $153M", 0.19, Icons.Default.Circle, Color(0xFF9C27B0)),
            Token(8, "Jupiter Staked SOL", "JUP", "$213", "MCap: $925M • Vol: $5.0M", 1.40, Icons.Default.Circle, Color(0xFF64B5F6)),
            Token(9, "Plasma", "PLSMA", "$0.42", "MCap: $781M • Vol: $264M", 1.79, Icons.Default.Circle, Color(0xFF795548)),
            Token(10, "Aerodrome", "AERO", "$0.78", "MCap: $710M • Vol: $28.2M", 3.71, Icons.Default.Circle, Color(0xFF00BCD4)),
            Token(11, "MYX Finance", "MYX", "$3.07", "MCap: $633M • Vol: $39.8M", 13.91, Icons.Default.Circle, Color(0xFF607D8B)),
        )
    }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.statusBarsPadding()) {
                TopNavBar(
                    title = "Trending tokens",
                    onSettingsClick = { /* Handle settings/profile click */ },
                    onSearchClick = { /* Handle search click */ }
                )
            }
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // --- Filters and Tab Row ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 24H Dropdown Button
                FilterButton("24H", leadingIcon = Icons.Default.ArrowDropDown)
                Spacer(Modifier.width(8.dp))

                // 'All' selected chip
                FilterChip("All", isSelected = true)
                Spacer(Modifier.width(8.dp))

                // Network Chips (BNB Smart Chain, Solana, Ethereum)
                NetworkChip("BNB Smart Chain", TrustBlue)
                Spacer(Modifier.width(8.dp))
                NetworkChip("Solana", Color(0xFF00FFA3)) // Solana color
                Spacer(Modifier.width(8.dp))
                NetworkChip("Ethereum", Color(0xFF627EEA)) // Ethereum color
                Spacer(Modifier.width(8.dp))
            }
            Divider(color = Color(0xFFEEEEEE), thickness = 1.dp)

            // --- List Header ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Market cap", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.weight(1f))
                Text("Volume", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.weight(1f))
                Text(
                    text = "% Price change",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier.width(80.dp),
                    textAlign = TextAlign.End // <-- CORRECTED: Must use TextAlign.End
                )
            }

            // --- Trending Tokens List ---
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(trendingTokens) { token ->
                    // This call is now correct with the updated function signature
                    TrendingTokenItem(token) { onTokenClick(token) }
                    Divider(color = Color(0xFFEEEEEE), thickness = 1.dp)
                }
            }
        }
    }
}


@Composable
fun FilterButton(text: String, leadingIcon: ImageVector) {
    Button(
        onClick = { /* Handle filter click */ },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFF0F0F0), // Light grey background
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
        modifier = Modifier.height(32.dp)
    ) {
        Text(text, fontSize = 14.sp)
        Icon(leadingIcon, contentDescription = null, modifier = Modifier.size(18.dp))
    }
}

@Composable
fun FilterChip(text: String, isSelected: Boolean) {
    val backgroundColor = if (isSelected) TrustBlue else Color(0xFFF0F0F0)
    val contentColor = if (isSelected) Color.White else Color.Black

    Card(
        onClick = { /* Handle chip click */ },
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.height(32.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text, fontSize = 14.sp, color = contentColor, fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal)
        }
    }
}

@Composable
fun NetworkChip(networkName: String, color: Color) {
    Card(
        onClick = { /* Handle network click */ },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0)), // Light grey background
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.height(32.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(color)
            )
            Spacer(Modifier.width(6.dp))
            Text(networkName, fontSize = 14.sp, color = Color.Black)
        }
    }
}