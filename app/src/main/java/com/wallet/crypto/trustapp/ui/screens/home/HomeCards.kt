// com/wallet/crypto/trustapp/ui/screens/home/HomeCards.kt
package com.wallet.crypto.trustapp.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
fun AlphaTokenCard(token: Token) {
    val growthColor = if (token.changePercent >= 0) Color(0xFF00C853) else Color(0xFFFF1744)
    val growthSign = if (token.changePercent >= 0) "+" else ""

    Card(
        modifier = Modifier.width(170.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(32.dp)
                        .background(token.iconColor),
                    contentAlignment = Alignment.Center
                ) { /* Placeholder */ }
                Spacer(modifier = Modifier.width(8.dp))
                Text(token.symbol, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(token.price, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Row {
                Text(token.subText, color = Color.Gray, fontSize = 12.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${growthSign}${String.format(Locale.US, "%.2f", token.changePercent)}%",
                    color = growthColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun EarnCard(earn: EarnOpportunity) {
    Card(
        modifier = Modifier.width(170.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "Earn up to",
                fontSize = 13.sp,
                color = Color.Gray
            )
            Text(
                text = "${String.format(Locale.US, "%.2f", earn.apy)}%",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TrustBlue
            )
            Text(
                text = "/ year",
                fontSize = 13.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(24.dp)
                        .background(earn.iconColor)
                ) { /* Placeholder */ }
                Spacer(modifier = Modifier.width(8.dp))
                Text("on ${earn.platform}", fontSize = 13.sp, color = Color.Gray)
            }
        }
    }
}


@Composable
fun QuestsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Complete quests", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("Earn up to 200 points per day", color = Color.Gray, fontSize = 13.sp)
            }
            Button(
                onClick = { /* Go Action */ },
                colors = ButtonDefaults.buttonColors(containerColor = TrustBlue),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Go", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}