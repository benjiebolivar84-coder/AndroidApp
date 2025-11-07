package com.wallet.crypto.trustapp.ui.mydebug

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.wallet.crypto.trustapp.corelibs.service.WalletCoreService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDebugScreen(onBackClick: () -> Unit) {
    val walletService = remember { WalletCoreService() }
    var mnemonic by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Debug Tools") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                val (m, a) = walletService.createNewWallet()
                mnemonic = m
                address = a
            }) {
                Text("Generate New Wallet")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (mnemonic.isNotEmpty()) {
                Text("Mnemonic:", style = MaterialTheme.typography.titleMedium)
                Text(mnemonic, modifier = Modifier.padding(8.dp))
            }

            if (address.isNotEmpty()) {
                Text("Ethereum Address:", style = MaterialTheme.typography.titleMedium)
                Text(address, modifier = Modifier.padding(8.dp))
            }
        }
    }
}
