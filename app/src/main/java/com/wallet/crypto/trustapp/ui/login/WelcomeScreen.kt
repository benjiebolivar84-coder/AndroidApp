package com.wallet.crypto.trustapp.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WelcomeScreen(
    onCreateNewWalletClick: () -> Unit,
    onIAlreadyHaveWalletClick: () -> Unit
) {
    val trustBlue = Color(0xFF0052FF)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
            .windowInsetsPadding(WindowInsets(0.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            // Placeholder for the 3D Cube Image
            Spacer(modifier = Modifier.height(200.dp))

            Spacer(modifier = Modifier.height(64.dp))

            Text(
                text = "Explore a limitless world of dApps",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.Black,
                lineHeight = 32.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onCreateNewWalletClick,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = trustBlue)
                ) {
                    Text("Create new wallet", fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onIAlreadyHaveWalletClick,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray.copy(alpha = 0.5f))
                ) {
                    Text("I already have a wallet", fontSize = 16.sp, color = Color.Black)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Terms and Privacy Text
                Text(
                    text = buildAnnotatedString {
                        append("By tapping any button you agree and consent to our ")
                        withStyle(style = SpanStyle(color = trustBlue, textDecoration = TextDecoration.Underline)) {
                            append("Terms of Service")
                        }
                        append(" and ")
                        withStyle(style = SpanStyle(color = trustBlue, textDecoration = TextDecoration.Underline)) {
                            append("Privacy Policy")
                        }
                        append(".")
                    },
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}