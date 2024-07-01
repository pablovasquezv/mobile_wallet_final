package com.vasquezsoftwaresolutions.mobile_wallet_final.model.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.local.dao.TransactionDao
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.local.entity.TransactionEntity

/**
 *@autor Pablo
 *@create 24-06-2024 21:38
 *@project mobile_wallet_final
 *@Version 1.0
 */
@Database(
    entities = [TransactionEntity::class],
    version = 1
)
abstract class WalletDataBase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: WalletDataBase? = null

        fun getDatabase(context: Context): WalletDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WalletDataBase::class.java,
                    "walletDb"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}