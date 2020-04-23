package se.tink.commons.businessdays

import org.joda.time.DateTime

data class DateComponents(
    val year: Int,
    val month: Int,
    val day: Int
)

fun DateComponents.toDateTime(): DateTime = DateTime(year, month, day, 0, 0)
fun DateTime.toDateComponents() = DateComponents(year, monthOfYear, dayOfMonth)
