package com.sneha.weather.data.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * Created by Sneha on 20-08-2024.
 */
fun String.toDate(
    dateFormat: String = "hh:mm:ss a",
    timeZone: TimeZone = TimeZone.getTimeZone("UTC")
): Date {
    val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
    parser.timeZone = timeZone
    return parser.parse(this) ?: Date()
}

fun Date.convertDateToTimeFormat(timeZone: String): String {
    val formatter = SimpleDateFormat("hh:mm:ss a", Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone(timeZone)
    return formatter.format(this)
}