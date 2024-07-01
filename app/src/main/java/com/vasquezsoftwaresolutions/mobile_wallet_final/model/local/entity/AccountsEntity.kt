package com.vasquezsoftwaresolutions.mobile_wallet_final.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *@autor Pablo
 *@create 30-06-2024 19:14
 *@project mobile_wallet_final
 *@Version 1.0
 */
@Entity(tableName = "table_accounts")
data class AccountsEntity(
    @PrimaryKey
    val id: Long,
    val creationDate: String,
    val money: String,
    val isBlocked: Boolean,
    val userId: Long,
    val createdAt: String,
    val updatedAt: String
)
