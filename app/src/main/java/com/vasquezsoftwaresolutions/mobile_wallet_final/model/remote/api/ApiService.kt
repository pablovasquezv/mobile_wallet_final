package com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.api

import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.model.UserModel
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.request.DepositOrTransferRequest
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.request.LoginRequest
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.request.RegisterRequest
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.response.AccountResponse
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.response.CreateAccountResponse
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.response.DepositOrTransferResponse
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.response.LoginResponse
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.response.PaginatedTransactionResponse
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

/**
 *@autor Pablo
 *@create 24-06-2024 19:22
 *@project mobile_wallet_final
 *@Version 1.0
 */
interface ApiService {
    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("/auth/me")
    fun getUserDetails(@Header("Authorization") token: String): Call<UserModel>

    @POST("/users")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("/accounts")
    fun createAccount(@Header("Authorization") token: String): Call<CreateAccountResponse>

    @GET("/accounts/me")
    fun getUserAccountsDetails(@Header("Authorization") token: String): Call<List<AccountResponse>>

    @POST("/accounts/{id}")
    fun depositarOtransferir(
        @Header("Authorization") token: String,
        @Path("id") accountId: Long?,
        @Body request: DepositOrTransferRequest
    ): Call<DepositOrTransferResponse>

    @GET("/transactions")
    suspend fun getTransactions(@Header("Authorization") token: String): PaginatedTransactionResponse
}