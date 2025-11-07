package com.wallet.crypto.trustapp.util

import android.content.Context

object PrefsHelper {
    private const val PREF_NAME = "trust_wallet_prefs"
    private const val KEY_REGISTERED = "registered"
    private const val KEY_BIOMETRIC_ALLOWED = "biometric_allowed"

    fun isRegistered(context: Context): Boolean =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_REGISTERED, false)

    fun setRegistered(context: Context, value: Boolean) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit().putBoolean(KEY_REGISTERED, value).apply()
    }

    fun isBiometricAllowed(context: Context): Boolean =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_BIOMETRIC_ALLOWED, false)

    fun setBiometricAllowed(context: Context, value: Boolean) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit().putBoolean(KEY_BIOMETRIC_ALLOWED, value).apply()
    }
}
