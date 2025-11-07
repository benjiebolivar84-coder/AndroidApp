import android.content.Context
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

fun authenticateWithBiometrics(
    context: Context,
    onSuccess: () -> Unit,
    onFailure: () -> Unit
) {
    val fragmentActivity = context as? FragmentActivity
    if (fragmentActivity == null) {
        Log.e("BiometricCheck", "Context is not a FragmentActivity")
        onFailure()
        return
    }

    // Check biometric availability
    val biometricManager = BiometricManager.from(fragmentActivity)
    val canAuthenticate = biometricManager.canAuthenticate()
    when (canAuthenticate) {
        BiometricManager.BIOMETRIC_SUCCESS -> Log.d("BiometricCheck", "Biometric supported and ready")
        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> Log.e("BiometricCheck", "No biometric hardware")
        BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> Log.e("BiometricCheck", "Biometric hardware unavailable")
        BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> Log.e("BiometricCheck", "No biometrics enrolled")
        else -> Log.e("BiometricCheck", "Unknown biometric error: $canAuthenticate")
    }

    if (canAuthenticate != BiometricManager.BIOMETRIC_SUCCESS) {
        onFailure()
        return
    }

    val executor = ContextCompat.getMainExecutor(fragmentActivity)
    val biometricPrompt = BiometricPrompt(
        fragmentActivity,
        executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onSuccess()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                onFailure()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Log.e("BiometricCheck", "Authentication error: $errorCode - $errString")
                onFailure()
            }
        }
    )

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Authenticate with biometrics")
        .setSubtitle("Use your fingerprint or face to continue")
        .setNegativeButtonText("Cancel")
        .build()

    biometricPrompt.authenticate(promptInfo)
}
