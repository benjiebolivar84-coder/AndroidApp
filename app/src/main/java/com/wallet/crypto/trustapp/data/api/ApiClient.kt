package com.wallet.crypto.trustapp.data.api

import ApiService
import android.content.Context
import com.wallet.crypto.trustapp.config.LocalConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


object ApiClient {

    private fun createOkHttpClient(context: Context) = OkHttpClient.Builder()
        .addInterceptor { chain: Interceptor.Chain ->
            try {
                chain.proceed(chain.request())
            } catch (e: IOException) {
                NetworkToast.show(context, throwable = e)
                throw e
            }
        }
        .build()

    private fun createRetrofit(context: Context): Retrofit {
        val baseUrl = LocalConfig.getDynamicBaseUrl(context)
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(createOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun createApiService(context: Context): ApiService {
        return createRetrofit(context).create(ApiService::class.java)
    }
}
