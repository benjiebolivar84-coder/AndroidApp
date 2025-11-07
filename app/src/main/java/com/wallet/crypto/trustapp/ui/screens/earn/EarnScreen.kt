package com.wallet.crypto.trustapp.ui.screens.earn

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wallet.crypto.trustapp.ui.screens.home.TrustBlue // Reusing TrustBlue constant

@Composable
fun EarnHeaderCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Placeholder Image/Icon area (Simulating the token icon)
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF627EEA)) // Ethereum color
                ) { /* Image Placeholder */ }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Stake your ETH & start earning", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    Text("Learn more", color = TrustBlue, fontSize = 14.sp)
                }
            }
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Go to stake",
                tint = Color.Gray
            )
        }
    }
}

@Composable
fun EarnScreen() {
    // --- Mock Data ---
    val stablecoinSection = EarnSection(
        title = "Stablecoin Earn",
        subtitle = "Earn stablecoin with flexibility and low risk",
        tokens = listOf(
            EarnToken(1, "USDT", "Ethereum", "3.75% APY", Color(0xFF26A69A), Icons.Default.Circle, true, listOf("Bonus")),
            EarnToken(2, "USDC", "Ethereum", "5.6% APY", Color(0xFF1E90FF), Icons.Default.Circle, true, listOf("Bonus")),
            EarnToken(3, "USDA", "Ethereum", "7.14% APY", Color(0xFF4CAF50), Icons.Default.Circle, true),
        )
    )

    val nativeStakingSection = EarnSection(
        title = "Native Staking",
        subtitle = "Stake and Delegate coins to earn rewards",
        tokens = listOf(
            EarnToken(4, "ETH", "Ethereum", "2.22% APR", Color(0xFF627EEA), Icons.Default.Circle, false, listOf("Over 45K stakers")),
            EarnToken(5, "BNB", "Smart Chain", "13.04% APR", Color(0xFFF3BA2F), Icons.Default.Circle, false),
            EarnToken(6, "SOL", "Solana", "6.7% APR", Color(0xFF00FFA3), Icons.Default.Circle, false),
            EarnToken(7, "TRX", "Tron", "4.19% APR", Color(0xFFFF4500), Icons.Default.Circle, false),
            EarnToken(8, "DOT", "Polkadot", "14.93% APR", Color(0xFFE6007A), Icons.Default.Circle, false),
        )
    )

    val tabs = remember { listOf("Earn Hub", "Trust Alpha", "My Earnings") }
    var selectedTabIndex by remember { mutableStateOf(0) }
    // --- End Mock Data ---


    // Since the TopAppBar is handled by the external Scaffold/BottomNav,
    // we only need the content column here.

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // 1. Tab Bar (Always visible at the top of the content)
        item {
            // Note: The screenshot shows a very minimalistic, unstyled TabRow below the app bar.
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.White,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = TrustBlue
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                title,
                                color = if (selectedTabIndex == index) TrustBlue else Color.Gray,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    )
                }
            }
        }

        // 2. Introductory Card
        item {
            EarnHeaderCard()
        }

        // 3. Stablecoin Section
        item {
            EarnSectionHeader(
                title = stablecoinSection.title,
                subtitle = stablecoinSection.subtitle
            )
        }
        items(stablecoinSection.tokens) { token ->
            EarnTokenItem(token)
            Divider(color = Color(0xFFEEEEEE), thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
        }

        // 4. Native Staking Section
        item {
            Spacer(modifier = Modifier.height(16.dp))
            EarnSectionHeader(
                title = nativeStakingSection.title,
                subtitle = nativeStakingSection.subtitle
            )
        }
        items(nativeStakingSection.tokens) { token ->
            EarnTokenItem(token)
            Divider(color = Color(0xFFEEEEEE), thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
        }

        // 5. Disclaimer/Footer
        item {
            Text(
                "Stablecoin Earn involves third-party DeFi protocols. Yields are subject to market conditions, protocol performance, and smart contract risks. Trust Wallet does not guarantee returns. Service may be unavailable in restricted regions due to regulatory requirements. Use subject to Terms of Service.",
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun EarnSectionHeader(title: String, subtitle: String?) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                if (subtitle != null) {
                    Text(subtitle, color = Color.Gray, fontSize = 14.sp)
                }
            }
            // Arrow icon for 'View All' equivalent
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "View all $title",
                tint = Color.Gray
            )
        }
    }
}