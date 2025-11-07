import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wallet.crypto.trustapp.data.api.ApiClient
import com.wallet.crypto.trustapp.data.api.NetworkToast
import com.wallet.crypto.trustapp.data.model.Wallet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _wallets = MutableStateFlow<List<Wallet>>(emptyList())
    val wallets = _wallets.asStateFlow()

    fun loadWallets(context: Context) {
        viewModelScope.launch {
            val api = ApiClient.createApiService(context)
            try {
                val response = api.getWallets()
                if (response.isSuccessful) {
                    _wallets.value = response.body() ?: emptyList()
                } else {
                    NetworkToast.show(context, httpCode = response.code())
                }
            } catch (e: Exception) {
                NetworkToast.show(context, throwable = e)
                Log.e("HomeViewModel", "Error loading wallets: ${e.message}")
            }
        }
    }
}
