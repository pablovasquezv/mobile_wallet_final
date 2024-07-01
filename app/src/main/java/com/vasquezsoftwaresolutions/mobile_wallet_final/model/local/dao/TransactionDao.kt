package com.vasquezsoftwaresolutions.mobile_wallet_final.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.local.entity.TransactionEntity

/**
 *@autor Pablo
 *@create 24-06-2024 21:39
 *@project mobile_wallet_final
 *@Version 1.0
 */
@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactions(transactions: List<TransactionEntity>)

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): LiveData<List<TransactionEntity>>

    @Query("DELETE FROM transactions")
    suspend fun deleteAll()
}