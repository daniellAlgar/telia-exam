package com.algar.model

import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime

data class FiveDayDetails(
    val dt: Long,
    @SerializedName("dt_txt")
    val date: DateTime,
    val weather: ArrayList<Weather>,
    val main: FiveDayMain
)