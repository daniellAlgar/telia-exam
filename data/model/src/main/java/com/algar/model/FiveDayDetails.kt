package com.algar.model

import com.google.gson.annotations.SerializedName

data class FiveDayDetails(
    val dt: Long,
    @SerializedName("dt_txt")
    val date: String,
    val weather: ArrayList<Weather>,
    val main: FiveDayMain
)