package com.wallet.crypto.trustapp.corelibs.service

import com.trustwallet.core.CoinType
import com.trustwallet.core.HDWallet

class WalletCoreService {

    fun createNewWallet(): Pair<String, String> {
        val wallet = HDWallet(128, "")
        val mnemonic = wallet.mnemonic
        val ethAddress = wallet.getAddressForCoin(CoinType.Ethereum)
        return Pair(mnemonic, ethAddress)
    }

    fun importWallet(mnemonic: String): Pair<String, String> {
        val wallet = HDWallet(mnemonic, "")
        val ethAddress = wallet.getAddressForCoin(CoinType.Ethereum)
        return Pair(mnemonic, ethAddress)
    }

}
