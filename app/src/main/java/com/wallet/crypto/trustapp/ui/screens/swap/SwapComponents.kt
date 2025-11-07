package com.wallet.crypto.trustapp.ui.screens.swap

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wallet.crypto.trustapp.ui.screens.home.Token
import com.wallet.crypto.trustapp.ui.screens.home.TrustBlue // Reusing TrustBlue constant

@Composable
fun TokenInputCard(
    token: Token,
    amount: String,
    isFromToken: Boolean,
    onSelectTokenClick: () -> Unit,
    onBuyClick: (() -> Unit)? = null // Optional buy button for 'From' token
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7)), // Light grey background
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Label (From / To)
                Text(
                    text = if (isFromToken) "From" else "To",
                    color = Color.Gray,
                    fontSize = 14.sp
                )

                // Balance and Buy Button (Only for 'From' Token)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("0", color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.width(4.dp))
                    Text(if (isFromToken) "Buy" else "", color = TrustBlue, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.clickable { onBuyClick?.invoke() })
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Token Selector Button (Left side)
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.White)
                        .clickable(onClick = onSelectTokenClick)
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Token Icon (using CircleShape placeholder like the others)
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(24.dp)
                            .background(token.iconColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(token.symbol.first().toString(), color = Color.White, fontSize = 12.sp)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    // Token Symbol and Name
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(token.symbol, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                        Text(token.name, color = Color.Gray, fontSize = 10.sp)
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Select token",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Amount Input (Right side)
                Text(
                    text = amount, // Should eventually be an input field
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.End
                )
            }
        }
    }
}