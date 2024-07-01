package com.vasquezsoftwaresolutions.mobile_wallet_final.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

/**
 *@autor Pablo
 *@create 27-06-2024 14:29
 *@project mobile_wallet_final
 *@Version 1.0
 */
fun String.toReadableDate(): String {
    val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    isoFormat.timeZone = TimeZone.getTimeZone("UTC")
    val date = isoFormat.parse(this)

    val readableFormat = SimpleDateFormat("MMM dd, hh:mm a", Locale.getDefault())
    return date?.let { readableFormat.format(it) } ?: this
}