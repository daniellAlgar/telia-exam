package com.algar.model

import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.util.Locale
import kotlin.collections.ArrayList

data class FiveDayDetails(
    val dt: Long,
    @SerializedName("dt_txt")
    val date: String,
    val weather: ArrayList<Weather>,
    val main: FiveDayMain
)

/**
 * Turns the [FiveDayDetails.date] into a fully qualified week day, e.g. Saturday.
 */
fun FiveDayDetails.prettyDate(): String {
    val formatter = DateTimeFormat.forPattern("y-M-d H:m:s")
    val date = DateTime.parse(date, formatter)
    val prettyFormatter = DateTimeFormat.forPattern("EEEE")
    val weekDay = prettyFormatter.withLocale(Locale.getDefault()).print(date)
    val timeFormatter = DateTimeFormat.forPattern("H:mm")
    val time = timeFormatter.print(date)
    return "$weekDay $time"
}