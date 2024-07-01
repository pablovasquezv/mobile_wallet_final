package com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.api

import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.model.UserModel
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.request.LoginRequest
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.response.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
/**
 *@autor Pablo
 *@create 24-06-2024 19:08
 *@project mobile_wallet_final
 *@Version 1.0
 */
interface LoginService {
    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("/auth/me")
    fun getUserDetails(@Header("Authorization") token: String): Call<UserModel>

}