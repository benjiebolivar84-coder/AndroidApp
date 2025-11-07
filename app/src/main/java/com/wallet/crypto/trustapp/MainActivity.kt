package com.wallet.crypto.trustapp

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.wallet.crypto.trustapp.ui.navigation.BottomNav
import com.wallet.crypto.trustapp.ui.theme.TrustWalletTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.wallet.crypto.trustapp.util.DarkThemeHelper
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue



class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            System.loadLibrary("TrustWalletCore")
            Log.i("MainActivity", "WalletCore initialized successfully")
        } catch (ex: Exception) {
            Log.e("MainActivity", "Native lib failed: ${ex.message}", ex)
        }

        enableEdgeToEdge()

        setContent {
            // ✅ Must be inside a composable
            var isDarkMode by remember {
                mutableStateOf(DarkThemeHelper.isDarkMode(this@MainActivity))
            }

            TrustWalletTheme(darkTheme = isDarkMode) {
                BottomNav(
                    hostActivity = this@MainActivity,
                    onToggleTheme = {
                        val newValue = !isDarkMode
                        isDarkMode = newValue
                        DarkThemeHelper.setDarkMode(this@MainActivity, newValue)
                    }
                )
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TrustWalletTheme {
        Greeting("Trust Wallet")
    }
}


