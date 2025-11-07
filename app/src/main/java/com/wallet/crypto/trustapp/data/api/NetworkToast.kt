// FILE: java/com/wallet/crypto/trustapp/data/api/NetworkToast.kt
package com.wallet.crypto.trustapp.data.api

import android.content.Context
import android.util.Log
import android.widget.Toast
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object NetworkToast {

    var isDev: Boolean = true  // <-- set false in prod/stage

    fun show(context: Context, httpCode: Int? = null, throwable: Throwable? = null) {
        if (!isDev) return

        val message = when {
            httpCode != null -> mapHttpCode(httpCode)
            throwable != null -> mapThrowable(throwable)
            else -> "Unknown network error"
        }

        Log.e("NetworkToast", "Network error: $message", throwable)


        android.os.Handler(android.os.Looper.getMainLooper()).post {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }


    private fun mapHttpCode(code: Int): String = when (code) {
        400 -> "Bad request (400)"
        401 -> "Unauthorized (401)"
        403 -> "Forbidden (403)"
        404 -> "Not found (404)"
        408 -> "Request timeout (408)"
        500 -> "Server error (500)"
        502 -> "Bad gateway (502)"
        503 -> "Service unavailable (503)"
        504 -> "Gateway timeout (504)"
        else -> "Network error ($code)"
    }

    private fun mapThrowable(t: Throwable): String = when (t) {
        is UnknownHostException -> "No internet connection"
        is SocketTimeoutException -> "Connection timeout"
        is IOException -> "IO error"+ t.localizedMessage
        else -> t.localizedMessage ?: "Unknown network error"
    }
}
