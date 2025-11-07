package com.wallet.crypto.trustapp.ui.navigation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.*
import authenticateWithBiometrics
import com.wallet.crypto.trustapp.data.repository.DevicePassCodeRepository
import com.wallet.crypto.trustapp.ui.mydebug.MyDebugScreen
import com.wallet.crypto.trustapp.ui.screens.discover.DiscoverScreen
import com.wallet.crypto.trustapp.ui.screens.earn.EarnScreen
import com.wallet.crypto.trustapp.ui.screens.home.HomeScreen
import com.wallet.crypto.trustapp.ui.screens.login.FingerprintBiometricDialog
import com.wallet.crypto.trustapp.ui.screens.login.PasscodeScreen
import com.wallet.crypto.trustapp.ui.screens.login.PasscodeScreenMode
import com.wallet.crypto.trustapp.ui.screens.login.WelcomeScreen
import com.wallet.crypto.trustapp.ui.screens.settings.QrCodeScannerScreen
import com.wallet.crypto.trustapp.ui.screens.settings.SettingsScreen
import com.wallet.crypto.trustapp.ui.screens.swap.SwapScreen
import com.wallet.crypto.trustapp.ui.screens.trending.TrendingScreen
import com.wallet.crypto.trustapp.util.PrefsHelper
import com.wallet.crypto.trustapp.util.getDeviceId
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

enum class LoginFlowState {
    WELCOME,
    PASSCODE,
    MAIN_APP
}

@Composable
fun BottomNav(hostActivity: FragmentActivity,
              onToggleTheme: () -> Unit) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Trending,
        BottomNavItem.Swap,
        BottomNavItem.Earn,
        BottomNavItem.Discover
    )

    val navController = rememberNavController()
    val trustBlue = Color(0xFF0052FF)
    val context = hostActivity

    val coroutineScope = rememberCoroutineScope()

    var currentFlowState by remember {
        mutableStateOf(
            if (PrefsHelper.isRegistered(context)) LoginFlowState.PASSCODE
            else LoginFlowState.WELCOME
        )
    }
    var showBiometricPrompt by remember { mutableStateOf(false) }

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val config = getAppBarConfig(currentRoute)
    val isFullScreenOrSecondaryRoute = currentRoute == "qr_scanner" || currentRoute == "settings"

    Box(modifier = Modifier.fillMaxSize()) {
        when (currentFlowState) {
            LoginFlowState.WELCOME -> {
                WelcomeScreen(
                    onCreateNewWalletClick = { currentFlowState = LoginFlowState.PASSCODE },
                    onIAlreadyHaveWalletClick = { currentFlowState = LoginFlowState.PASSCODE }
                )
            }

            LoginFlowState.PASSCODE -> {
                //val coroutineScope = rememberCoroutineScope()
                var isLoading by remember { mutableStateOf(false) }

                PasscodeScreen(
                    mode = if (PrefsHelper.isRegistered(context))
                        PasscodeScreenMode.ENTER_EXISTING
                    else
                        PasscodeScreenMode.CREATE_NEW,
                    onPasscodeSuccess = { passcode ->
                        coroutineScope.launch {
                            isLoading = true
                            val deviceId = getDeviceId(context)
                            val registered = PrefsHelper.isRegistered(context)

                            val isSuccess = if (registered) {
                                DevicePassCodeRepository.validate(context, deviceId, passcode)
                            } else {
                                DevicePassCodeRepository.register(context, deviceId, passcode)
                            }

                            if (isSuccess && !registered) {
                                PrefsHelper.setRegistered(context, true)
                            }

                            isLoading = false

                            if (isSuccess) {
                                currentFlowState = LoginFlowState.MAIN_APP
                                // FIX 1: Only prompt for biometric setup if it hasn't been allowed yet.
                                if (!PrefsHelper.isBiometricAllowed(context)) {
                                    Log.d("BottomNav", "Triggering biometric setup prompt")
                                    showBiometricPrompt = true
                                } else {
                                    Log.d("BottomNav", "Biometric already allowed, skipping setup prompt")
                                }

                            } else {
                                Toast.makeText(context, "Invalid or failed passcode", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    onFingerprintClick = {
                        Log.d("BottomNav", "Fingerprint clicked, showing biometric dialog")


                        if (PrefsHelper.isBiometricAllowed(context)) {
                            authenticateWithBiometrics(
                                context = hostActivity,
                                onSuccess = {
                                    Toast.makeText(hostActivity, "Biometric auth successful. Moving to Main App.", Toast.LENGTH_SHORT).show()
                                    currentFlowState = LoginFlowState.MAIN_APP
                                },
                                onFailure = {
                                    Toast.makeText(hostActivity, "Biometric authentication failed", Toast.LENGTH_SHORT).show()
                                }
                            )
                        } else {
                            showBiometricPrompt = true
                        }
                    },
                    onBackClick = { currentFlowState = LoginFlowState.WELCOME }
                )

                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0x66000000)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }
            }

            LoginFlowState.MAIN_APP -> {
                Scaffold(
                    topBar = {
                        if (config.showDefaultTopBar && currentRoute != "qr_scanner") {
                            TrustAppBar(title = { Text(config.title) }, actions = config.actions ?: {})
                        }
                    },
                    bottomBar = {
                        if (!isFullScreenOrSecondaryRoute) {
                            NavigationBar(containerColor = Color.White) {
                                items.forEach { item ->
                                    val selected = currentRoute == item.route
                                    NavigationBarItem(
                                        selected = selected,
                                        onClick = {
                                            navController.navigate(item.route) {
                                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                        icon = { Icon(if (selected) item.filledIcon else item.outlinedIcon, contentDescription = item.label, tint = if (selected) trustBlue else Color.Gray) },
                                        label = { Text(item.label, color = if (selected) trustBlue else Color.Gray) },
                                        alwaysShowLabel = true
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    val bottomPadding = if (isFullScreenOrSecondaryRoute) 0.dp else innerPadding.calculateBottomPadding()
                    val customTopPadding = when {
                        isFullScreenOrSecondaryRoute -> 0.dp
                        currentRoute == BottomNavItem.Home.route -> 0.dp
                        else -> innerPadding.calculateTopPadding()
                    }

                    NavHost(
                        navController = navController,
                        startDestination = BottomNavItem.Home.route,
                        modifier = Modifier.padding(
                            top = customTopPadding,
                            bottom = bottomPadding,
                            start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                            end = innerPadding.calculateEndPadding(LayoutDirection.Ltr)
                        )
                    ) {
                        composable(BottomNavItem.Home.route) {
                            HomeScreen(
                                onSettingsClick = { navController.navigate("settings") },
                                onQrCodeScannerClick = { navController.navigate("qr_scanner") }
                            )
                        }
                        composable(BottomNavItem.Trending.route) { TrendingScreen(onTokenClick = {}) }
                        composable(BottomNavItem.Swap.route) { SwapScreen() }
                        composable(BottomNavItem.Earn.route) { EarnScreen() }
                        composable(BottomNavItem.Discover.route) { DiscoverScreen() }
                        composable("debug_tools") {
                            MyDebugScreen(onBackClick = { navController.popBackStack() })
                        }
                        composable("settings") {
                            SettingsScreen(
                                onBackClick = {navController.popBackStack()},
                                onDebugClick = { navController.navigate("debug_tools")},
                                onToggleTheme = onToggleTheme

                            )
                        }
                        composable("qr_scanner") { QrCodeScannerScreen(onBackClick = { navController.popBackStack() }) }
                    }
                }
            }
        }

        // --- Biometric Dialog ---
        if (showBiometricPrompt) {
            FingerprintBiometricDialog(
                onAllow = {
                    showBiometricPrompt = false
                    Log.d("BottomNav", "Biometric allow clicked")
                    PrefsHelper.setBiometricAllowed(hostActivity, true)

                    coroutineScope.launch {
                        val deviceId = getDeviceId(context)
                        DevicePassCodeRepository.updateBiometricStatus(context, deviceId, true)
                        Log.d("BottomNav", "Biometric status updated to true on backend.")
                    }

                    authenticateWithBiometrics(
                        context = hostActivity,
                        onSuccess = {
                            Toast.makeText(hostActivity, "Biometric authentication successful", Toast.LENGTH_SHORT).show()
                            currentFlowState = LoginFlowState.MAIN_APP
                                    },
                        onFailure = {
                            Toast.makeText(hostActivity, "Biometric authentication failed", Toast.LENGTH_SHORT).show()
                        }
                    )
                },
                onDeny = {
                    showBiometricPrompt = false
                    Log.d("BottomNav", "Biometric deny clicked")
                    PrefsHelper.setBiometricAllowed(hostActivity, false)
                },
                onDismiss = {
                    showBiometricPrompt = false
                    Log.d("BottomNav", "Biometric dialog dismissed")
                }
            )
        }
    }
}
