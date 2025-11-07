package com.wallet.crypto.trustapp.ui.screens.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Divider
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
fun DAppItemRow(item: DAppItem) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Rank Number
            Text(
                text = item.rank.toString(),
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = Color.Gray,
                modifier = Modifier.width(32.dp)
            )

            // Icon
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(36.dp)
                    .background(item.iconColor),
                contentAlignment = Alignment.Center
            ) {
                // Placeholder for actual logo
                Text(
                    text = item.name.first().toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(12.dp))

            // Text Content
            Column(modifier = Modifier.weight(1f)) {
                Text(item.name, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                Text(item.description, color = Color.Gray, fontSize = 12.sp, maxLines = 2)
            }
        }
        Divider(color = Color(0xFFEEEEEE), thickness = 1.dp)
    }
}