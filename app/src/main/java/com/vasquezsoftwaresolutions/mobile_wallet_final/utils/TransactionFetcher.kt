package com.vasquezsoftwaresolutions.mobile_wallet_final.utils

import android.util.Log
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.local.entity.TransactionEntity
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.repository.TransactionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *@autor Pablo
 *@create 27-06-2024 14:30
 *@project mobile_wallet_final
 *@Version 1.0
 */
class TransactionFetcher(
    private val transactionRepository: TransactionRepository
) {

    fun fetchTransactions(token: String, onComplete: (Boolean, List<TransactionEntity>?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val transactionsResponse = transactionRepository.fetchTransactionsFromApi("Bearer $token")
                val transactionsList = transactionsResponse.data.map { response ->
                    TransactionEntity(
                        amount = response.amount,
                        concept = response.concept,
                        date = response.date,
                        type = response.type,
                        accountID = response.accountId,
                        userID = response.userId,
                        toAccountID = response.toAccountId
                    )
                }
                transactionRepository.deleteAllTransactions()
                transactionRepository.saveTransactions(transactionsList)
                onComplete(true, transactionsList)
            } catch (e: Exception) {
                Log.e("TransactionFetcher", "Error fetching transactions", e)
                onComplete(false, null)
            }
        }
    }
}