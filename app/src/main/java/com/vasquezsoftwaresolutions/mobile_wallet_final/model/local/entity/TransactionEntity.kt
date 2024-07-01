package com.vasquezsoftwaresolutions.mobile_wallet_final.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *@autor Pablo
 *@create 24-06-2024 21:38
 *@project mobile_wallet_final
 *@Version 1.0
 */
@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Long,
    val concept: String,
    val date: String,
    val type: String,
    val accountID: Long,
    val userID: Long,
    val toAccountID: Long
)
