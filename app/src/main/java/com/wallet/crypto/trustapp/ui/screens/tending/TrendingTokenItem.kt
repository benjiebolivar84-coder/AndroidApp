package com.wallet.crypto.trustapp.ui.screens.trending

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable // Import needed for click
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wallet.crypto.trustapp.ui.screens.home.Token
import java.util.Locale

@Composable
fun TrendingTokenItem(
    token: Token,
    onClick: () -> Unit // ADDED: The required lambda parameter
) {
    val growthColor = if (token.changePercent >= 0) Color(0xFF00C853) else Color(0xFFFF1744)
    val growthSign = if (token.changePercent >= 0) "+" else ""

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick) // ADDED: Make the entire row clickable
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Token Icon/Logo (Placeholder using existing icon properties)
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(36.dp)
                .background(token.iconColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = token.symbol.first().toString(),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
        Spacer(modifier = Modifier.width(12.dp))

        // Token Info
        Column(modifier = Modifier.weight(1f)) {
            Text(token.name, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = Color.Black)
            Text(token.subText, color = Color.Gray, fontSize = 12.sp)
        }

        // Price and Change Percent
        Column(horizontalAlignment = Alignment.End) {
            Text(token.price, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = Color.Black)
            Text(
                text = "${growthSign}${String.format(Locale.US, "%.2f", token.changePercent)}%",
                color = growthColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp
            )
        }
    }
}