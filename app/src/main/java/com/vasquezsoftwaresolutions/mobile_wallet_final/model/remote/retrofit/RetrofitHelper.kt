package com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.retrofit

import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.api.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *@autor Pablo
 *@create 24-06-2024 19:26
 *@project mobile_wallet_final
 *@Version 1.0
 */
object RetrofitHelper {
    private const val BASE_URL = "http://wallet-main.eba-ccwdurgr.us-east-1.elasticbeanstalk.com"

    /**
     *  Create an OkHttpClient to be able to make a log of the network traffic
     *   Crear un interceptor de registro
     */

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    /**
     *  Crear un cliente HTTP utilizando OkHttpClient.Builder.
     */
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)// Agregar el interceptor de registro al cliente
        .build()

    /**
     *  Create the Retrofit instance
     */
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}