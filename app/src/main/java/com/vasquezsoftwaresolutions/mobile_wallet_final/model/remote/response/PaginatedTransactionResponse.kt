package com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.response

/**
 *@autor Pablo
 *@create 24-06-2024 19:03
 *@project mobile_wallet_final
 *@Version 1.0
 */
data class PaginatedTransactionResponse(
    val previousPage: String?,
    val nextPage: String?,
    val data: List<TransactionResponse>
)
