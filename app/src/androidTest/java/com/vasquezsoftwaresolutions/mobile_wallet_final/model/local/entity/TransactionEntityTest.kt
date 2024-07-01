package com.vasquezsoftwaresolutions.mobile_wallet_final.model.local.entity

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * @autor Pablo
 * @create 30-06-2024 19:27
 * @project mobile_wallet_final
 * @Version 1.0
 */

@RunWith(JUnit4::class)
class TransactionEntityTest {
    private lateinit var transactionEntity: TransactionEntity

    @Before
    fun setup() {
        transactionEntity = TransactionEntity(
            id = 1,
            amount = 1000,
            concept = "Payment",
            date = "2022-06-30",
            type = "debit",
            accountID = 123456789,
            userID = 987654321,
            toAccountID = 987654321
        )
    }

    @After
    fun tearDown() {

        // Actions for cleanup and resource release
        // For example, you can reset the transactionEntity object to null

    }

    @Test
    fun testTransactionEntityConstructor() {
        // verify that the values assigned in the constructor are correct
        assert(transactionEntity.id.toInt() == 1)
        assert(transactionEntity.amount.toLong().toInt() == 1000)
        assert(transactionEntity.concept == "Payment")
        assert(transactionEntity.date == "2022-06-30")
        assert(transactionEntity.type == "debit")
        assert(transactionEntity.accountID.toLong().toInt() == 123456789)
        assert(transactionEntity.userID.toLong().toInt() == 987654321)
        assert(transactionEntity.toAccountID.toLong().toInt() == 987654321)
    }
}