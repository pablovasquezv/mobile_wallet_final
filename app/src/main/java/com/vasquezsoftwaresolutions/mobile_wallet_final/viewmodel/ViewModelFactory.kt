package com.vasquezsoftwaresolutions.mobile_wallet_final.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vasquezsoftwaresolutions.mobile_wallet_final.utils.SharedPreferencesManager
import com.vasquezsoftwaresolutions.mobile_wallet_final.utils.TransactionFetcher

/**
 *@autor Pablo
 *@create 27-06-2024 14:39
 *@project mobile_wallet_final
 *@Version 1.0
 */
class ViewModelFactory(
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val transactionFetcher: TransactionFetcher
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                LoginViewModel(sharedPreferencesManager, transactionFetcher) as T
            }

            modelClass.isAssignableFrom(TransactionViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                TransactionViewModel(sharedPreferencesManager) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}