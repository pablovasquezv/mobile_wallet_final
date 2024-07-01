package com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.response

import com.google.gson.annotations.SerializedName

/**
 *@autor Pablo
 *@create 24-06-2024 19:04
 *@project mobile_wallet_final
 *@Version 1.0
 */
data class TransactionResponse(
    val amount: Long,
    val concept: String,
    val date: String,
    val type: String,
    val accountId: Long,
    val userId: Long,
    @SerializedName("to_account_id")
    val toAccountId: Long
)
