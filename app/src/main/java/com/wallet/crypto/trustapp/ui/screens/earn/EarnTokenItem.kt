package com.wallet.crypto.trustapp.ui.screens.earn

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
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
import com.wallet.crypto.trustapp.ui.screens.home.TrustBlue // Reusing TrustBlue constant

@Composable
fun EarnTag(text: String, isPrimary: Boolean) {
    val containerColor = if (isPrimary) TrustBlue.copy(alpha = 0.1f) else Color(0xFFF0F0F0)
    val contentColor = if (isPrimary) TrustBlue else Color.Gray

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(containerColor)
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            text = text,
            color = contentColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun EarnTokenItem(token: EarnToken) {
    val rateColor = Color(0xFF00C853) // Green for all rates shown in the screenshot

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // 1. Icon and Token Info
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
            // Icon
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(36.dp)
                    .background(token.iconColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(token.icon, contentDescription = token.symbol, tint = Color.White, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(token.symbol, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    token.tags.forEach { tag ->
                        // Use a heuristic: 'Bonus' is primary (blue), others are secondary (gray)
                        EarnTag(text = tag, isPrimary = tag == "Bonus")
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }
                Text(token.network, color = Color.Gray, fontSize = 12.sp)
            }
        }

        // 2. Rate (APY/APR)
        Text(
            text = token.rate,
            color = rateColor,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}