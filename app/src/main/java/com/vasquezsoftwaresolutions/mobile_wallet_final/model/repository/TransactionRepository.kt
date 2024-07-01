package com.vasquezsoftwaresolutions.mobile_wallet_final.model.repository

import android.util.Log
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.local.dao.TransactionDao
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.local.entity.TransactionEntity
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.api.ApiService
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.response.PaginatedTransactionResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *@autor Pablo
 *@create 24-06-2024 21:42
 *@project mobile_wallet_final
 *@Version 1.0
 */
class TransactionRepository(
    private val transactionDao: TransactionDao,
    private val apiService: ApiService,
) {

    suspend fun fetchTransactionsFromApi(token: String): PaginatedTransactionResponse {
        return withContext(Dispatchers.IO) {
            apiService.getTransactions(token)
        }
    }

    suspend fun saveTransactions(transactions: List<TransactionEntity>) {
        transactionDao.insertTransactions(transactions)
        Log.d("TransactionRepository", "Transacciones guardadas en la base de datos")
    }

    suspend fun deleteAllTransactions() {
        transactionDao.deleteAll()
    }
}