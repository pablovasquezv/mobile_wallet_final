package com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.response

/**
 *@autor Pablo
 *@create 24-06-2024 19:01
 *@project mobile_wallet_final
 *@Version 1.0
 */
data class CreateAccountResponse(
    val id: Long,
    val userID: Long,
    val updatedAt: String,
    val createdAt: String
)