package com.wallet.crypto.trustapp.ui.screens.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Divider
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
fun LatestItemRow(item: LatestItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon (Larger)
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(48.dp)
                .background(item.iconColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(item.icon, contentDescription = item.title, tint = Color.White, modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))

        // Text Content
        Column(modifier = Modifier.weight(1f)) {
            Text(item.title, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            Text(item.subtitle, color = Color.Gray, fontSize = 12.sp, maxLines = 1)
        }

        // Arrow Icon
        Icon(Icons.Default.ArrowForward, contentDescription = "Go", tint = Color.Gray, modifier = Modifier.size(20.dp))
    }
    Divider(color = Color(0xFFEEEEEE), thickness = 1.dp)
}