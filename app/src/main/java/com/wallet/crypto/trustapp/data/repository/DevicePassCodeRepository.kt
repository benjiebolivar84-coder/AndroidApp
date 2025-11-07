package com.wallet.crypto.trustapp.data.repository

import android.content.Context
import com.wallet.crypto.trustapp.data.api.ApiClient
import com.wallet.crypto.trustapp.data.api.NetworkToast
import com.wallet.crypto.trustapp.data.model.BiometricStatusRequest
import com.wallet.crypto.trustapp.data.model.DevicePassCode
import com.wallet.crypto.trustapp.data.model.ValidatePasscodeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object DevicePassCodeRepository {

    suspend fun register(context: Context, deviceId: String, passcode: String): Boolean = withContext(Dispatchers.IO) {
        val api = ApiClient.createApiService(context)
        try {
            val response = api.registerPasscode(DevicePassCode(id = deviceId, name = deviceId, passcode = passcode))
            if (!response.isSuccessful) {
                NetworkToast.show(context, httpCode = response.code())
            }
            response.isSuccessful
        } catch (e: Exception) {
            NetworkToast.show(context, throwable = e)
            false
        }
    }

    suspend fun validate(context: Context, deviceId: String, passcode: String): Boolean = withContext(Dispatchers.IO) {
        val api = ApiClient.createApiService(context)
        try {
            val response = api.validatePasscode(DevicePassCode(id = deviceId, name = deviceId, passcode = passcode))
            if (!response.isSuccessful) {
                NetworkToast.show(context, httpCode = response.code())
            }
            // Access the `data` property safely
            response.body()?.data?.valid == true
        } catch (e: Exception) {
            NetworkToast.show(context, throwable = e)
            false
        }
    }

    suspend fun updateBiometricStatus(context: Context, deviceId: String, isEnabled: Boolean): Boolean = withContext(Dispatchers.IO) {
        val api = ApiClient.createApiService(context)
        try {
            val requestBody = BiometricStatusRequest(isBiometricEnabled = isEnabled)
            val response = api.updateBiometricStatus(deviceId, requestBody)

            if (!response.isSuccessful) {
                NetworkToast.show(context, httpCode = response.code())
            }
            return@withContext response.isSuccessful
        } catch (e: Exception) {
            NetworkToast.show(context, throwable = e)
            return@withContext false
        }
    }


}
