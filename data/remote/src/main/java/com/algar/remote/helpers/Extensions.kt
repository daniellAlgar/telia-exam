package com.algar.remote.helpers

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.ISODateTimeFormat

/** Convert string to [DateTime] */
fun String.toDate(): DateTime {
    val formatter = DateTimeFormat.forPattern("y-M-d H:m:s")
    return DateTime.parse(this, formatter)
}

/**
 * Transforms a date to an ISO-formatted string
 */
fun DateTime.toIsoString(): String = ISODateTimeFormat.dateTime().print(this)
