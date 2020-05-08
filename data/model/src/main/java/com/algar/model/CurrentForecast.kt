package com.algar.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrentForecast(
    @PrimaryKey
    val id: Int,
    val dt: Long,
    val main: MainForecast,
    val weather: ArrayList<Weather>,
    val name: String
)