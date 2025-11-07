package com.wallet.crypto.trustapp.data.model

data class ValidatePasscodeResponse(
    val success: Boolean,
    val data: ValidatePasscodeData
)

data class ValidatePasscodeData(
    val valid: Boolean
)
