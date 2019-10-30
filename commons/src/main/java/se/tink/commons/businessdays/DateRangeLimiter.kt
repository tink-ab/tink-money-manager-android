package se.tink.commons.businessdays

import android.os.Parcelable
import java.util.Calendar

interface DateRangeLimiter : Parcelable {
    val minYear: Int
    val maxYear: Int
    val startDate: Calendar
    val endDate: Calendar
    fun isOutOfRange(year: Int, month: Int, day: Int): Boolean
    fun setToNearestDate(day: Calendar): Calendar
}
