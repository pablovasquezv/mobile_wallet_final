package com.vasquezsoftwaresolutions.mobile_wallet_final.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 *@autor Pablo
 *@create 30-06-2024 20:59
 *@project mobile_wallet_final
 *@Version 1.0
 */
fun <T> LiveData<T>.getOrAwaitValue(): T {
    var value: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(t: T) {
            value = t
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)
    latch.await(2, TimeUnit.SECONDS)
    return value!!
}