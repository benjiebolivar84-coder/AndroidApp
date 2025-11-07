package com.wallet.crypto.trustapp.ui.screens.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
fun DiscoverScreen() {
    // --- Mock Data ---
    val dappTabs = remember { listOf("Featured", "BSC", "DEX", "Lending", "Yield") }
    var selectedDAppTabIndex by remember { mutableStateOf(0) }

    val featuredDApps = remember {
        listOf(
            DAppItem(1, 1, "Four", "FOUR.meme is a go-to platform for easily launching meme tokens...", Color(0xFF4CAF50), Icons.Default.Circle),
            DAppItem(2, 2, "Aster", "Decentralized perpetual contracts. Multi-chain, liquid, secure.", Color(0xFFFFA500), Icons.Default.Circle),
            DAppItem(3, 3, "Aave", "Aave is an Open Source and Non-Custodial protocol to earn inter...", Color(0xFF7B1FA2), Icons.Default.Circle),
        )
    }

    val latestItems = remember {
        listOf(
            LatestItem("$100,000 in \$WBAl!", "Get ready for WhiteBridge Network's TGE on Oct 15. Complete tasks t...", Color(0xFF424242), Icons.Default.Euro),
            LatestItem("Participated in Trust Alpha?", "We value community feedback. Share yours with us →", TrustBlue, Icons.Default.Diversity3),
            LatestItem("Introducing Trust Moon", "Our accelerator is live! Grow any stage Web3 project with Trust Walle...", Color(0xFFFFA726), Icons.Default.Star)
        )
    }
    // --- End Mock Data ---

    // We assume TopAppBar and BottomNavBar are handled by the external Scaffold.
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // 1. Search Bar (Located just below the Top App Bar)
        item {
            OutlinedTextField(
                value = "",
                onValueChange = { /* Handle search input */ },
                placeholder = { Text("Search or enter dApp URL", color = Color.Gray) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF7F7F7),
                    unfocusedContainerColor = Color(0xFFF7F7F7),
                    cursorColor = TrustBlue,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )
        }

        // 2. Quests Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Info, contentDescription = "Quests", tint = TrustBlue, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Quests", color = TrustBlue, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Get rewards", color = Color.Gray, fontSize = 14.sp)
            }
        }

        // 3. Carousel/Banner
        item {
            // Note: Implementing a full carousel is complex, so we simulate the content area.
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(3) {
                    Card(
                        modifier = Modifier
                            .width(300.dp)
                            .height(120.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0FF)), // Light Purple/Blue background
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp).fillMaxSize(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text("Meme Rush LIVE!", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, color = Color.Black)
                            Text("Trade Binance Wallet's Meme Rush + Four.meme tokens in-app", color = Color.Black.copy(alpha = 0.8f), fontSize = 14.sp, maxLines = 2)
                            Spacer(Modifier.height(8.dp))
                            Button(onClick = { /* Swap Now */ }, colors = ButtonDefaults.buttonColors(containerColor = TrustBlue)) {
                                Text("SWAP NOW")
                            }
                        }
                    }
                }
            }
        }

        // 4. Explore DApps Section
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
                Text("Explore dApps", fontWeight = FontWeight.Bold, fontSize = 20.sp)

                // DApp Tabs
                TabRow(
                    selectedTabIndex = selectedDAppTabIndex,
                    containerColor = Color.White,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedDAppTabIndex]),
                            color = TrustBlue
                        )
                    },
                    divider = {}
                ) {
                    dappTabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedDAppTabIndex == index,
                            onClick = { selectedDAppTabIndex = index },
                            text = {
                                Text(
                                    title,
                                    color = if (selectedDAppTabIndex == index) TrustBlue else Color.Gray,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        )
                    }
                }

                // DApp List (Featured - visible tab content)
                Spacer(modifier = Modifier.height(8.dp))
                featuredDApps.forEach { item ->
                    DAppItemRow(item)
                }

                // View All Button
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("See All", color = TrustBlue, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "See All", tint = TrustBlue, modifier = Modifier.size(20.dp))
                }
            }
        }

        // 5. Latest Section
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
                Text("Latest", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(8.dp))

                // Latest Items List
                latestItems.forEach { item ->
                    LatestItemRow(item)
                }

                // View All Button (Same style)
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("See All", color = TrustBlue, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "See All", tint = TrustBlue, modifier = Modifier.size(20.dp))
                }
            }
        }
    }
}