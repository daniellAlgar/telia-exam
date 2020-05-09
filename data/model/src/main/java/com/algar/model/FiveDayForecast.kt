package com.algar.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FiveDayForecast(
    @PrimaryKey
    val id: Long,
    val list: ArrayList<FiveDayDetails>
)