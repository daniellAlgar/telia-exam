package com.algar.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FiveDayForecast(
    @PrimaryKey
    val id: Int,
    val name: String,
    val list: ArrayList<FiveDayDetails>
)