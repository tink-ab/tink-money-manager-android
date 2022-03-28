package se.tink.commons.businessdays

import androidx.annotation.VisibleForTesting
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@Parcelize
data class SwedishHolidayDateRangeLimiter @Inject constructor(
    val locale: Locale
) : DateRangeLimiter {

    private val now
        get() = DateTime.now()

    override val minYear: Int
        get() = now.year
    override val maxYear: Int
        get() = now.year + 1
    override val startDate: Calendar
        get() = now.toCalendar(locale)
    override val endDate: Calendar
        get() = now.plusYears(1).toCalendar(locale)

    override fun isOutOfRange(year: Int, month: Int, day: Int): Boolean {

        // Month is 0 indexed for some reason!
        val monthActual = month + 1

        return with(DateComponents(year, monthActual, day)) {
            isInThePast() || isWeekend() || isSwedishHoliday() || isDeFactoSwedishHoliday()
        }
    }

    override fun setToNearestDate(day: Calendar): Calendar {
        while (isOutOfRange(
                day.get(Calendar.YEAR),
                day.get(Calendar.MONTH),
                day.get(Calendar.DAY_OF_MONTH)
            )
        ) {
            day.add(Calendar.DAY_OF_MONTH, 1)
        }
        return day
    }
}

@VisibleForTesting
internal fun DateComponents.isWeekend() =
    toDateTime().let { it.dayOfWeek == 6 || it.dayOfWeek == 7 }

// / - Note: [List of public holidays in Sweden]
// (https://en.wikipedia.org/wiki/Public_holidays_in_Sweden#List_of_public_holidays_in_Sweden)
@VisibleForTesting
internal fun DateComponents.isSwedishHoliday() =
    isNewYearsDay() ||
        isEpiphany() ||
        isGoodFriday() ||
        isEasterSunday() ||
        isEasterMonday() ||
        isInternationalWorkersDay() ||
        isAscensionDay() ||
        isPentecost() ||
        isNationalDayOfSweden() ||
        isMidsummersDay() ||
        isAllSaintsDay() ||
        isChristmasDay() ||
        isSecondDayOfChristmas()

// / - Note: The de facto holidays are almost always treated as official holidays by employers,
// so most employees working regular office hours do not work these days.
@VisibleForTesting
internal fun DateComponents.isDeFactoSwedishHoliday() =
    isMidsummersEve() || isChristmasEve() || isNewYearsEve()

internal fun DateComponents.isInThePast() = toDateTime().isBefore(DateTime.now().withMillisOfDay(0))
