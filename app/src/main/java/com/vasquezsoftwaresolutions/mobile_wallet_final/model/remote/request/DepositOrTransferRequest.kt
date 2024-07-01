package com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.request

/**
 *@autor Pablo
 *@create 24-06-2024 18:45
 *@project mobile_wallet_final
 *@Version 1.0
 */
data class DepositOrTransferRequest(
    val type: String,
    val concept: String,
    val amount: Long
)
