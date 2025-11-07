package com.wallet.crypto.trustapp.ui.screens.settings

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import com.google.common.util.concurrent.ListenableFuture
import java.util.concurrent.Executor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrCodeScannerScreen(onBackClick: () -> Unit) {

    val context = LocalContext.current

    // State to track if camera permission has been granted
    var hasCamPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    // Launcher for requesting permission
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasCamPermission = isGranted
        }
    )

    // Check permission on first composition
    LaunchedEffect(Unit) {
        if (!hasCamPermission) {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Scan QR code") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle gallery click */ }) {
                        Icon(Icons.Default.Image, contentDescription = "Gallery")
                    }
                    IconButton(onClick = { /* Handle manual input click */ }) {
                        Icon(Icons.Default.Create, contentDescription = "Manual Input")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color.Black,
        // FIX: Remove default window insets padding so content can draw under the status bar
        contentWindowInsets = WindowInsets(0.dp)
    ) { paddingValues ->

        // The outer Box fills the entire screen, ignoring status bar padding
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (hasCamPermission) {
                // CameraPreviewView covers the entire screen, including behind the status bar
                CameraPreviewView(
                    modifier = Modifier.fillMaxSize()
                )

                // Overlay Box: Must respect the TopAppBar height (top padding)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        // Apply only the top padding calculated by Scaffold for the TopAppBar
                        .padding(top = paddingValues.calculateTopPadding())
                ) {
                    QrScanOverlay()
                }
            } else {
                // Permission Denied View: Respect all padding for centering
                Text(
                    text = "Camera permission required to scan QR codes.",
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .wrapContentSize(Alignment.Center)
                )
            }
        }
    }
}

// ... CameraPreviewView and QrScanOverlay composables remain unchanged ...

@Composable
fun CameraPreviewView(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { ctx ->
            val previewView = PreviewView(ctx).apply {
                this.scaleType = PreviewView.ScaleType.FILL_CENTER
            }

            val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> = ProcessCameraProvider.getInstance(ctx)
            val executor: Executor = ContextCompat.getMainExecutor(ctx)

            cameraProviderFuture.addListener({
                val cameraProvider = try {
                    cameraProviderFuture.get()
                } catch (e: Exception) {
                    return@addListener
                }

                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    cameraProvider.unbindAll()

                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview
                    )
                } catch (e: Exception) {
                    // Log the exception here if binding fails (e.g., no camera available)
                }
            }, executor)

            previewView
        }
    )
}

@Composable
fun QrScanOverlay() {
    val frameColor = Color.White
    val cornerLength = 25.dp
    val strokeWidth = 3.dp

    Canvas(modifier = Modifier.fillMaxSize()) {
        val squareSize = size.minDimension * 0.6f
        val offsetX = (size.width - squareSize) / 2
        val offsetY = (size.height - squareSize) / 2

        val cornerLengthPx = cornerLength.toPx()
        val strokeWidthPx = strokeWidth.toPx()

        // Draw top-left corner
        drawPath(
            path = Path().apply {
                moveTo(offsetX, offsetY + cornerLengthPx)
                lineTo(offsetX, offsetY)
                lineTo(offsetX + cornerLengthPx, offsetY)
            },
            color = frameColor,
            style = Stroke(width = strokeWidthPx)
        )

        // Draw top-right corner
        drawPath(
            path = Path().apply {
                moveTo(offsetX + squareSize - cornerLengthPx, offsetY)
                lineTo(offsetX + squareSize, offsetY)
                lineTo(offsetX + squareSize, offsetY + cornerLengthPx)
            },
            color = frameColor,
            style = Stroke(width = strokeWidthPx)
        )

        // Draw bottom-left corner
        drawPath(
            path = Path().apply {
                moveTo(offsetX, offsetY + squareSize - cornerLengthPx)
                lineTo(offsetX, offsetY + squareSize)
                lineTo(offsetX + cornerLengthPx, offsetY + squareSize)
            },
            color = frameColor,
            style = Stroke(width = strokeWidthPx)
        )

        // Draw bottom-right corner
        drawPath(
            path = Path().apply {
                moveTo(offsetX + squareSize - cornerLengthPx, offsetY + squareSize)
                lineTo(offsetX + squareSize, offsetY + squareSize)
                lineTo(offsetX + squareSize, offsetY + squareSize - cornerLengthPx)
            },
            color = frameColor,
            style = Stroke(width = strokeWidthPx)
        )
    }
}