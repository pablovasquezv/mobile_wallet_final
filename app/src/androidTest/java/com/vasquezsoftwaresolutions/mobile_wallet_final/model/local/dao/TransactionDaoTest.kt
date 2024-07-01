package com.vasquezsoftwaresolutions.mobile_wallet_final.model.local.dao

import com.vasquezsoftwaresolutions.mobile_wallet_final.model.local.entity.TransactionEntity
import kotlinx.coroutines.runBlocking
import org.junit.Test

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.local.database.WalletDataBase

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

/**
 *@autor Pablo
 *@create 30-06-2024 20:21
 *@project mobile_wallet_final
 *@Version 1.0
 */
@RunWith(AndroidJUnit4::class)
class TransactionDaoTest {
    // Crear una instancia de TransactionDao (puedes usar un mock si es necesario)

    private lateinit var dDao: TransactionDao
    private lateinit var dDB: WalletDataBase


    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        dDB = Room.inMemoryDatabaseBuilder(context, WalletDataBase::class.java).build()
        dDao = dDB.transactionDao()
    }

    @After
    fun shutdown() {
        dDB.close()
    }

    @Test
    fun testiIsertAllTransactions() = runBlocking {
        val transationsList = listOf(
            TransactionEntity(
                id = 1,
                amount = 1000,
                concept = "Payment",
                date = "2022-06-30",
                type = "debit",
                accountID = 123456789,
                userID = 987654321,
                toAccountID = 987654321
            ),
            TransactionEntity(
                id = 2,
                amount = 2000,
                concept = "Payment",
                date = "2022-06-30",
                type = "debit",
                accountID = 123456789,
                userID = 987654321,
                toAccountID = 987654321
            ),
            TransactionEntity(
                id = 3,
                amount = 3000,
                concept = "Payment",
                date = "2022-06-30",
                type = "debit",
                accountID = 123456789,
                userID = 987654321,
                toAccountID = 987654321
            )
        )
        dDao.insertTransactions(transationsList)

        val dragonBallLiveData = dDao.getAllTransactions()
        val listTransactions = dragonBallLiveData.value ?: emptyList()

        MatcherAssert.assertThat(listTransactions, CoreMatchers.not(emptyList()))
        MatcherAssert.assertThat(listTransactions.size, CoreMatchers.equalTo(3))
    }
}