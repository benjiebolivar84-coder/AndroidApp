package com.wallet.crypto.trustapp.config

import android.content.Context
import android.net.wifi.WifiManager
import java.util.Locale


val ipAdd= "10.136.198.56"
object LocalConfig {

    fun getDynamicBaseUrl(context: Context): String {
        return try {
            "http://$ipAdd:8083/"
        } catch (e: Exception) {
            e.printStackTrace()
            "http://127.0.0.1:8083/"
        }
    }
}
