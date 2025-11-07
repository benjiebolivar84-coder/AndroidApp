package com.wallet.crypto.trustapp.ui.screens.swap

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsToggleRow(
    title: String,
    description: String,
    initialState: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    showInfoIcon: Boolean = false
) {
    var isChecked by remember { mutableStateOf(initialState) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                isChecked = !isChecked
                onCheckedChange(isChecked)
            }
            .padding(vertical = 12.dp, horizontal = 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Text Content
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
                if (showInfoIcon) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Info",
                        tint = Color.Gray,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                fontSize = 13.sp,
                color = Color.Gray,
                lineHeight = 18.sp
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Toggle Switch
        Switch(
            checked = isChecked,
            onCheckedChange = {
                isChecked = it
                onCheckedChange(it)
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF5090FF), // Trust Blue-like color
                uncheckedThumbColor = Color.LightGray,
                uncheckedTrackColor = Color(0xFFEEEEEE)
            )
        )
    }
}

@Composable
fun SlippageButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.height(36.dp),
        shape = ButtonDefaults.outlinedShape,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (isSelected) Color(0xFFE6F0FF) else Color.Transparent, // Light blue background
            contentColor = if (isSelected) Color(0xFF5090FF) else Color.Gray
        ),
        border = BorderStroke(
            1.dp,
            if (isSelected) Color(0xFF5090FF) else Color.LightGray
        ),
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        Text(text = text, fontSize = 14.sp)
    }
}