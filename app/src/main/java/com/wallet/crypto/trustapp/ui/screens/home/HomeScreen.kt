package com.wallet.crypto.trustapp.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wallet.crypto.trustapp.ui.theme.TrustWalletTheme


@Composable
fun HomeScreen(
    onSettingsClick: () -> Unit,
    onQrCodeScannerClick: () -> Unit // RE-ADDED: QR SCANNER PARAMETER
) {
    val topMovers = remember {
        listOf(
            Token(1, "dogwifhat", "WIF", "$0.58", "MCap: $580 • Vol: $419", 5.06, Icons.Default.Circle, Color.Red),
            Token(2, "TROLL", "TROLL", "$0.11", "MCap: $117 • Vol: $9.32", 4.17, Icons.Default.Circle, Color.Blue),
        )
    }

    val popularTokens = remember {
        listOf(
            Token(1, "Bitcoin", "BTC", "$1,13,129", "MCap: $2.25 • Vol: $69.50", -1.45, Icons.Default.Circle, Color(0xFFF7931A)),
            Token(2, "Ethereum", "ETH", "$4,104", "MCap: $495 • Vol: $49.30", -1.0, Icons.Default.Circle, Color(0xFF627EEA)),
            Token(3, "BNB", "BNB", "$1,246", "MCap: $173 • Vol: $11.50", -3.55, Icons.Default.Circle, Color(0xFFF3BA2F)),
        )
    }

    val alphaTokens = remember {
        listOf(
            Token(1, "Alpha Token 1", "AOP", "$0.08", "$6.11", 3.74, Icons.Default.Circle, Color(0xFF673AB7)),
            Token(2, "Alpha Token 2", "KOGE", "$48", "$1.19", 0.02, Icons.Default.Circle, Color(0xFFFF9800)),
        )
    }

    val earnOpportunities = remember {
        listOf(
            EarnOpportunity("Stargaze", 25.09, "Stargaze", Color(0xFFF9A825)),
            EarnOpportunity("Juno", 22.24, "Juno", Color(0xFFE57373)),
            EarnOpportunity("Cosmos", 16.33, "Cosmos", Color(0xFF64B5F6)),
        )
    }

    val tabs = remember { listOf("Top", "BNB", "ETH", "SOL") }
    var selectedTabIndex by remember { mutableStateOf(0) }

    val listState = rememberLazyListState()

    val scrolledDown by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 300
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0))
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp),
            state = listState
        ) {

            item {
                Column(modifier = Modifier.statusBarsPadding()) {
                    HomeHeader(
                        totalBalance = "$4379.3499",
                        changePercent = "$2 (0%)",
                        onSettingsClick = onSettingsClick,
                        onQrCodeScannerClick = onQrCodeScannerClick // PASSED BOTH ACTIONS
                    )
                }
            }

            item {
                HomeActionsRow()
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Text("Top movers", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    topMovers.forEachIndexed { index, token ->
                        TopMoverItem(token, index + 1)
                        Divider(color = Color(0xFFEEEEEE))
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = {  }) {
                            Text("View All", color = Color.Black, fontWeight = FontWeight.SemiBold)
                            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "View All", tint = Color.Black)
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Column(modifier = Modifier.background(Color.White).padding(top = 16.dp, bottom = 8.dp)) {
                    Text("Popular tokens", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(horizontal = 16.dp))
                    Spacer(modifier = Modifier.height(16.dp))

                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        containerColor = Color.White,
                        indicator = {},
                        divider = {}
                    ) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                text = {
                                    Text(
                                        title,
                                        fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                                        color = if (selectedTabIndex == index) TrustBlue else Color.Gray
                                    )
                                },
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(if (selectedTabIndex == index) Color(0xFFE3F2FD) else Color.Transparent)
                            )
                        }
                    }

                    Text("Top tokens by total market cap", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))

                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        popularTokens.forEachIndexed { _, token ->
                            PopularTokenItem(token)
                            if (token.id < popularTokens.size) {
                                Divider(color = Color(0xFFEEEEEE))
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = {  }) {
                            Text("View All", color = Color.Black, fontWeight = FontWeight.SemiBold)
                            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "View All", tint = Color.Black)
                        }
                    }
                }
            }

            item {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    Text("Alpha tokens", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(horizontal = 16.dp))
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(alphaTokens) { token ->
                            AlphaTokenCard(token)
                        }
                    }
                }
            }

            item {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Earn", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("View All", color = Color.Gray, fontSize = 14.sp)
                            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "View All", tint = Color.Gray)
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(earnOpportunities) { earn ->
                            EarnCard(earn)
                        }
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    Text(
                        "Quests",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    QuestsCard()
                }
            }

            item {
                Text(
                    "Past performance is not a reliable indicator of future results. Data source is from CoinMarketCap.",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                Text(
                    "Subject to our Terms",
                    color = TrustBlue,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }

        if (scrolledDown) {
            CompactHomeHeader(onSettingsClick)
        }
    }
}

@Composable
fun CompactHomeHeader(onSettingsClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .statusBarsPadding()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onSettingsClick) {
            Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.Black)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "$0",
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
        }

        Row {
            // QR Scanner icon is intentionally absent in the compact header
            IconButton(onClick = {  }) {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Black)
            }
        }
    }
}