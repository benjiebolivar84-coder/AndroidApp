// com/wallet/crypto/trustapp/ui/screens/home/TokenListItems.kt
package com.wallet.crypto.trustapp.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

@Composable
fun TopMoverItem(coin: Token, rank: Int) {
    val growthColor = if (coin.changePercent >= 0) Color(0xFF00C853) else Color(0xFFFF1744)
    val growthSign = if (coin.changePercent >= 0) "+" else ""

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = rank.toString(),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray,
            modifier = Modifier.padding(end = 12.dp)
        )
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(36.dp)
                .background(coin.iconColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(coin.icon, contentDescription = coin.name, tint = Color.White, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(coin.name, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            Text(coin.subText, color = Color.Gray, fontSize = 12.sp)
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(coin.price, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Text(
                text = "${growthSign}${String.format(Locale.US, "%.2f", coin.changePercent)}%",
                color = growthColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun PopularTokenItem(token: Token) {
    val growthColor = if (token.changePercent >= 0) Color(0xFF00C853) else Color(0xFFFF1744)
    val growthSign = if (token.changePercent >= 0) "+" else ""

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = token.id.toString(),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray,
            modifier = Modifier.padding(end = 12.dp)
        )
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(36.dp)
                .background(token.iconColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = token.symbol,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(token.name, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            Text(token.mCapVol ?: "", color = Color.Gray, fontSize = 12.sp)
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(token.price, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Text(
                text = "${growthSign}${String.format(Locale.US, "%.2f", token.changePercent)}%",
                color = growthColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp
            )
        }
    }
}