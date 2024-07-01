package com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.api

import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.response.PaginatedTransactionResponse
import retrofit2.http.GET
import retrofit2.http.Header

/**
 *@autor Pablo
 *@create 24-06-2024 19:18
 *@project mobile_wallet_final
 *@Version 1.0
 */
interface Transactions {
    @GET("/transactinos")
    suspend fun getTransactions(@Header("Authorization") token: String): PaginatedTransactionResponse
}