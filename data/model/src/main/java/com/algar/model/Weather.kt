package com.algar.model

import android.annotation.SuppressLint

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
) {
    val capitalizedDescription: String
        @SuppressLint("DefaultLocale")
        get() = description.capitalize()
}
