package com.softbinator.presentation

import java.text.SimpleDateFormat
import java.util.Locale

fun toReadableDate(isoFormat: String): String = try {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val output = SimpleDateFormat("dd/MM/yyyy 'at' HH:mm:ss", Locale.getDefault())
    val date = sdf.parse(isoFormat)
    if (date == null) {
        ""
    } else {
        output.format(date)
    }
} catch (e: Exception) {
    ""
}