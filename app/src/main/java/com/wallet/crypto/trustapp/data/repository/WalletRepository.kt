import com.wallet.crypto.trustapp.data.model.Wallet

class   WalletRepository(private val api: ApiService) {
    suspend fun getWallets(): List<Wallet> {
        val response = api.getWallets()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else throw Exception("API error: ${response.code()}")
    }
}
