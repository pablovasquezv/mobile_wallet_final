package com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.request

import com.google.gson.annotations.SerializedName
/**
 *@autor Pablo
 *@create 24-06-2024 18:46
 *@project mobile_wallet_final
 *@Version 1.0
 */
data class RegisterRequest(
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val email: String,
    val password: String,
    val roleId: Int,
    val points: Int
)
