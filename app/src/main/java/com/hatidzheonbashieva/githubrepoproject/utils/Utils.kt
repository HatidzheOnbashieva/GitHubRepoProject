package com.hatidzheonbashieva.githubrepoproject.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun trimDate(date: String): String {
    val oldFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val newFormat = SimpleDateFormat("dd-MM-yyyy")
    val newDate = oldFormat.parse(date)
    return newFormat.format(newDate!!)
}