// FILE: java/com/wallet/crypto/trustapp/ui/screens/login/PasscodeScreen.kt
package com.wallet.crypto.trustapp.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

private const val MAX_PASSCODE_LENGTH = 6
private val KEYPAD_BUTTON_SIZE = 75.dp
private val INDICATOR_BOX_SIZE = 24.dp
private val KEYPAD_HORIZONTAL_MARGIN = 16.dp

private enum class PasscodeStage {
    CREATE,
    CONFIRM
}

enum class PasscodeScreenMode {
    CREATE_NEW,
    ENTER_EXISTING,
    CONFIRM_ACTION
}

@Composable
fun PasscodeScreen(
    mode: PasscodeScreenMode = PasscodeScreenMode.CREATE_NEW,
    onPasscodeSuccess: (String) -> Unit,
    onFingerprintClick: () -> Unit,
    onBackClick: () -> Unit
) {
    var passcode by remember { mutableStateOf("") }
    var initialPasscode by remember { mutableStateOf("") }
    var currentStage by remember {
        mutableStateOf(
            when (mode) {
                PasscodeScreenMode.CREATE_NEW -> PasscodeStage.CREATE
                else -> PasscodeStage.CONFIRM
            }
        )
    }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val darkBlue = Color(0xFF0052FF)
    val errorRed = Color(0xFFE53935)

    val screenTitle: String
    val helperText: String
    val helperTextColor: Color

    when (currentStage) {
        PasscodeStage.CREATE -> {
            screenTitle = when (mode) {
                PasscodeScreenMode.CREATE_NEW -> "Create passcode"
                else -> "Enter passcode"
            }
            helperText = when (mode) {
                PasscodeScreenMode.CREATE_NEW -> "Enter your passcode. Be sure to remember it."
                else -> "Enter your passcode to continue."
            }
            helperTextColor = Color.Gray
        }
        PasscodeStage.CONFIRM -> {
            screenTitle = when (mode) {
                PasscodeScreenMode.CREATE_NEW -> "Confirm passcode"
                else -> "Enter passcode"
            }
            if (errorMessage != null) {
                helperText = errorMessage!!
                helperTextColor = errorRed
            } else {
                helperText = when (mode) {
                    PasscodeScreenMode.CREATE_NEW -> "Re-enter your passcode. Be sure to remember it."
                    else -> ""
                }
                helperTextColor = Color.Gray
            }
        }
    }

    val onKeyClick: (String) -> Unit = { digit ->
        if (errorMessage != null) errorMessage = null
        if (passcode.length < MAX_PASSCODE_LENGTH) {
            passcode += digit
            if (passcode.length == MAX_PASSCODE_LENGTH) {
                when (currentStage) {
                    PasscodeStage.CREATE -> {
                        initialPasscode = passcode
                        passcode = ""
                        currentStage = PasscodeStage.CONFIRM
                    }
                    PasscodeStage.CONFIRM -> {
                        if (mode == PasscodeScreenMode.CREATE_NEW) {
                            if (passcode == initialPasscode) onPasscodeSuccess(passcode)
                            else {
                                errorMessage = "Those passwords didn't match!"
                                passcode = ""
                            }
                        } else {
                            onPasscodeSuccess(passcode)
                        }
                    }
                }
            }
        }
    }

    val onBackspaceClick: () -> Unit = {
        if (passcode.isNotEmpty()) passcode = passcode.dropLast(1)
        errorMessage = null
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets(0.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (mode != PasscodeScreenMode.ENTER_EXISTING && mode != PasscodeScreenMode.CONFIRM_ACTION) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 48.dp, start = 16.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Go Back",
                            tint = Color.Black
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = screenTitle,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(MAX_PASSCODE_LENGTH) { index ->
                        Box(
                            modifier = Modifier
                                .size(INDICATOR_BOX_SIZE)
                                .background(
                                    color = if (index < passcode.length) darkBlue else Color.LightGray.copy(alpha = 0.5f),
                                    shape = RoundedCornerShape(4.dp)
                                )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                if (helperText.isNotEmpty()) {
                    Text(
                        text = helperText,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = if (errorMessage != null) FontWeight.Bold else FontWeight.Normal
                        ),
                        color = helperTextColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            NumericKeypad(
                onKeyClick = onKeyClick,
                onBackspaceClick = onBackspaceClick,
                onFingerprintClick = onFingerprintClick
            )
        }
    }
}

@Composable
private fun NumericKeypad(
    onKeyClick: (String) -> Unit,
    onBackspaceClick: () -> Unit,
    onFingerprintClick: () -> Unit
) {
    val keys = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf("fingerprint", "0", "backspace")
    )
    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        keys.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = KEYPAD_HORIZONTAL_MARGIN),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                row.forEach { key ->
                    KeypadButton(
                        key = key,
                        onClick = when (key) {
                            "fingerprint" -> onFingerprintClick
                            "backspace" -> onBackspaceClick
                            else -> { -> onKeyClick(key) }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun KeypadButton(key: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(KEYPAD_BUTTON_SIZE)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        when (key) {
            "fingerprint" -> Icon(
                Icons.Default.Fingerprint,
                contentDescription = "Fingerprint Login",
                modifier = Modifier.size(28.dp),
                tint = Color.Gray
            )
            "backspace" -> Icon(
                Icons.Default.Backspace,
                contentDescription = "Backspace",
                modifier = Modifier.size(28.dp),
                tint = Color.Gray
            )
            else -> Text(
                text = key,
                fontSize = 28.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun FingerprintBiometricDialog(
    onAllow: () -> Unit,
    onDeny: () -> Unit,
    onDismiss: () -> Unit
){
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Biometric Login") },
        text = { Text("Scan your fingerprint or face for secure and convenient access to your account.") },
        confirmButton = {
            Button(onClick = onAllow) { Text("Confirm") }
        },
        dismissButton = {
            TextButton(onClick = onDeny) { Text("Deny") }
        }
    )
}
