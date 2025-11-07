// FILE: java/com/wallet/crypto/trustapp/ui/screens/settings/SettingsComponents.kt

package com.wallet.crypto.trustapp.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsItemRow(item: SettingsItem) {

    var isChecked by remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = item.onClick)
                .padding(vertical = 14.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Icon on the left
            item.icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = item.title,
                    tint = Color.Black.copy(alpha = 0.8f),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
            }

            // Title Text
            Text(
                text = item.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.weight(1f) // Fills available space
            )

            // Toggle on the right
            if (item.isToggle) {
                Spacer(modifier = Modifier.width(16.dp))
                Switch(
                    checked = isChecked,
                    onCheckedChange = {
                        isChecked = it
                        item.onClick() // Still invoke the click action
                    },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = Color(0xFF5090FF),
                        uncheckedTrackColor = Color(0xFFEEEEEE)
                    )
                )
            }
        }
        // Divider
        Divider(
            modifier = Modifier.padding(start = if (item.icon != null) 56.dp else 16.dp), // Intentional spacing if icon exists
            color = Color.LightGray,
            thickness = 0.5.dp
        )
    }
}