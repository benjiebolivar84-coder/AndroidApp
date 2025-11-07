package com.wallet.crypto.trustapp.ui.screens.swap

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwapSettingsBottomSheet(
    isVisible: Boolean,
    onDismissRequest: () -> Unit
) {
    if (!isVisible) return

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    // State for Slippage
    var slippagePercentage by remember { mutableStateOf(2.0f) }
    val presetSlippages = listOf(0.1f, 0.5f, 1.0f, 2.5f)

    ModalBottomSheet(
        onDismissRequest = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    onDismissRequest()
                }
            }
        },
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        containerColor = Color.White,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                // Header (Title and Close button)
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Swap settings",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    IconButton(onClick = onDismissRequest) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                // 1. Toggle Options
                Column(modifier = Modifier.fillMaxWidth()) {
                    SettingsToggleRow(
                        title = "Best price execution",
                        description = "Get fair swap prices by shielding your transactions from MEV",
                        initialState = true,
                        onCheckedChange = { /* Handle toggle state change */ },
                        showInfoIcon = true
                    )
                    Divider(color = Color(0xFFF0F0F0), thickness = 1.dp)

                    SettingsToggleRow(
                        title = "Unlimited allowance",
                        description = "Approve each token once and swap any amount, anytime",
                        initialState = true,
                        onCheckedChange = { /* Handle toggle state change */ }
                    )
                    Divider(color = Color(0xFFF0F0F0), thickness = 1.dp)

                    SettingsToggleRow(
                        title = "Thorchain streams",
                        description = "Gives better quote, but takes longer to process the swap.",
                        initialState = true,
                        onCheckedChange = { /* Handle toggle state change */ }
                    )
                    Divider(color = Color(0xFFF0F0F0), thickness = 1.dp)

                    SettingsToggleRow(
                        title = "Solana turbo swaps",
                        description = "Costs more SOL, but speeds up swaps",
                        initialState = false,
                        onCheckedChange = { /* Handle toggle state change */ }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 2. Max Slippage Section
                Text(
                    text = "Set max slippage",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "This helps you avoid drastic swap price changes. The swap will revert if the price shifts beyond this percentage.",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Slippage Control
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(8.dp)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Decrease Button
                    IconButton(
                        onClick = {
                            slippagePercentage = (slippagePercentage - 0.1f).coerceAtLeast(0.1f)
                        },
                        modifier = Modifier
                            .size(56.dp)
                            .background(Color(0xFFF7F7F7))
                    ) {
                        Text("-", fontSize = 28.sp, color = Color.Gray, fontWeight = FontWeight.Light)
                    }

                    // Value Display
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = String.format("%.1f", slippagePercentage),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = " %",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black
                        )
                    }

                    // Increase Button
                    IconButton(
                        onClick = { slippagePercentage = (slippagePercentage + 0.1f).coerceAtMost(10.0f) },
                        modifier = Modifier
                            .size(56.dp)
                            .background(Color(0xFFF7F7F7))
                    ) {
                        Text("+", fontSize = 28.sp, color = Color.Gray, fontWeight = FontWeight.Light)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Slippage Presets
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    presetSlippages.forEach { preset ->
                        SlippageButton(
                            text = "${preset.toString().removeSuffix(".0")}%",
                            isSelected = slippagePercentage == preset
                        ) {
                            slippagePercentage = preset
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp)) // Bottom padding
            }
        }
    )
}