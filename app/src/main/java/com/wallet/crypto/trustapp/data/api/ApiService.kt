import com.wallet.crypto.trustapp.data.model.BiometricStatusRequest
import com.wallet.crypto.trustapp.data.model.DevicePassCode
import com.wallet.crypto.trustapp.data.model.ValidatePasscodeResponse
import com.wallet.crypto.trustapp.data.model.Wallet
import com.wallet.crypto.trustapp.data.model.WalletRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.PUT
import retrofit2.http.Path


interface ApiService {
    @GET("wallets")
    suspend fun getWallets(): Response<List<Wallet>>

    @POST("wallets")
    suspend fun createWallet(@Body wallet: WalletRequest): Response<Wallet>

    // Register new device + passcode
    @POST("device-passcodes")
    suspend fun registerPasscode(@Body passCode: DevicePassCode): Response<Unit>

    @POST("device-passcodes/validate")
    suspend fun validatePasscode(@Body passCode: DevicePassCode): Response<ValidatePasscodeResponse>

    @PUT("device-passcodes/biometric/{deviceId}")
    suspend fun updateBiometricStatus(
        @Path("deviceId") deviceId: String,
        @Body request: BiometricStatusRequest
    ): Response<Unit>

}


