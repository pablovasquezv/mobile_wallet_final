package com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.response

/**
 *@autor Pablo
 *@create 24-06-2024 19:01
 *@project mobile_wallet_final
 *@Version 1.0
 */
data class AccountResponse(
    val id: Long,
    val creationDate: Any? = null,
    val money: Double?,
    val isBlocked: Boolean,
    val userId: Long,
    val createdAt: String,
    val updatedAt: String
)
