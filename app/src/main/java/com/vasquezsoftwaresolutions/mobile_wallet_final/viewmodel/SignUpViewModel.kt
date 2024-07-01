package com.vasquezsoftwaresolutions.mobile_wallet_final.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.model.UserModel
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.request.RegisterRequest
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.response.RegisterResponse
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.retrofit.RetrofitHelper
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *@autor Pablo
 *@create 27-06-2024 14:35
 *@project mobile_wallet_final
 *@Version 1.0
 */
class SignUpViewModel : ViewModel() {
    private val _registerResult = MutableLiveData<Boolean>()
    val registerResult: LiveData<Boolean> get() = _registerResult
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun signUp(user: UserModel) {
        viewModelScope.launch {
            val registerRequest =
                RegisterRequest(user.firstName, user.lastName, user.email, user.password, 1, 1000)
            RetrofitHelper.apiService.registerUser(registerRequest)
                .enqueue(object : Callback<RegisterResponse> {
                    override fun onResponse(
                        call: Call<RegisterResponse>,
                        response: Response<RegisterResponse>
                    ) {
                        if (response.isSuccessful) {
                            _registerResult.value = true
                            _errorMessage.value = null
                        } else {
                            // Manejar la respuesta de error
                            try {
                                val errorBody = response.errorBody()?.string()
                                val errorJson = errorBody?.let { JSONObject(it) }
                                val errorMessage = errorJson?.getString("error")
                                if (errorMessage != null) {
                                    if (errorMessage.contains("Duplicate entry")) {
                                        _errorMessage.value = "El correo electrónico ya existe"
                                    } else {
                                        _errorMessage.value = "Registro fallido: $errorMessage"
                                    }
                                }
                            } catch (e: Exception) {
                                _errorMessage.value = "Registro fallido: ${e.message}"
                            }
                            _registerResult.value = false
                        }
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        _registerResult.value = false
                        _errorMessage.value = "Registro fallido: ${t.message}"
                    }
                })
        }
    }
}