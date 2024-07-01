package com.vasquezsoftwaresolutions.mobile_wallet_final.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.local.entity.TransactionEntity
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.model.UserModel
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.request.LoginRequest
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.response.AccountResponse
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.response.CreateAccountResponse
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.response.LoginResponse
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.retrofit.RetrofitHelper
import com.vasquezsoftwaresolutions.mobile_wallet_final.utils.SharedPreferencesManager
import com.vasquezsoftwaresolutions.mobile_wallet_final.utils.TransactionFetcher
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *@autor Pablo
 *@create 27-06-2024 14:33
 *@project mobile_wallet_final
 *@Version 1.0
 */
class LoginViewModel(
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val transactionFetcher: TransactionFetcher? = null
) : ViewModel() {

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    private val _userDetails = MutableLiveData<UserModel>()

    private val _createAccountResult = MutableLiveData<Boolean>()
    val createAccountResult: LiveData<Boolean> get() = _createAccountResult

    private val _updateAccountResult = MutableLiveData<Boolean>()
    val updateAccountResult: LiveData<Boolean> get() = _updateAccountResult

    private val _accountDetailsUpdated = MutableLiveData<Boolean>()
    val accountDetailsUpdated: LiveData<Boolean> get() = _accountDetailsUpdated

    private val transactions = MutableLiveData<List<TransactionEntity>?>()

    fun login(email: String, password: String, isSignUp: Boolean = false) {
        val loginRequest = LoginRequest(email, password)
        RetrofitHelper.apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    val token = response.body()?.accessToken
                    if (token != null) {
                        sharedPreferencesManager.saveAuthToken(token)
                        getUserDetails(token, isSignUp)
                    } else {
                        _loginResult.value = false
                    }
                } else {
                    _loginResult.value = false
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _loginResult.value = false
            }
        })
    }

    private fun getUserDetails(token: String, isSignUp: Boolean) {
        RetrofitHelper.apiService.getUserDetails("Bearer $token").enqueue(object :
            Callback<UserModel> {
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    user?.let {
                        if (isSignUp) {
                            createAccount(token)
                        }
                        getUserAccountsDetails(token)
                        sharedPreferencesManager.saveUser(it)

                        _userDetails.value = it
                    }
                } else {
                    _loginResult.value = false
                }
            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                _loginResult.value = false
            }
        })
    }

    private fun createAccount(token: String) {
        RetrofitHelper.apiService.createAccount("Bearer $token")
            .enqueue(object : Callback<CreateAccountResponse> {
                override fun onResponse(
                    call: Call<CreateAccountResponse>,
                    response: Response<CreateAccountResponse>
                ) {
                    if (response.isSuccessful) {
                        _createAccountResult.value
                    }
                }

                override fun onFailure(call: Call<CreateAccountResponse>, t: Throwable) {
                    _createAccountResult.value = false
                }
            })
    }

    fun getUserAccountsDetails(token: String) {
        RetrofitHelper.apiService.getUserAccountsDetails("Bearer $token")
            .enqueue(object : Callback<List<AccountResponse>> {
                override fun onResponse(
                    call: Call<List<AccountResponse>>,
                    response: Response<List<AccountResponse>>
                ) {
                    if (response.isSuccessful) {
                        val accounts = response.body()
                        accounts?.let {
                            if (it.isNotEmpty()) {
                                val account = it[0]
                                val money = account.money?.toString() ?: "0"
                                sharedPreferencesManager.saveAccountData(money, account.id)
                                _accountDetailsUpdated.value = true
                            }
                        }
                        fetchTransactions(token)
                    } else {
                        _accountDetailsUpdated.value = false
                    }
                }

                override fun onFailure(call: Call<List<AccountResponse>>, t: Throwable) {
                    _accountDetailsUpdated.value = false
                }
            })
    }

    private fun fetchTransactions(token: String) {
        transactionFetcher?.fetchTransactions(token) { success, transactionsList ->
            if (success) {
                Log.d("Transactions", "Transactions from login: $transactionsList")
                transactions.postValue(transactionsList)
                _loginResult.postValue(true)
            } else {
                _loginResult.postValue(false)
            }
        }
    }

}
