package se.tink.commons.businessdays

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Locale

internal class SwedishHolidayDateRangeLimiterTest {

    lateinit var swedishHolidayDateRangeLimiter: SwedishHolidayDateRangeLimiter

    private fun date(year: Int, month: Int, day: Int): Calendar =
        GregorianCalendar(year, month, day)

    @BeforeEach
    fun setUp() {
        swedishHolidayDateRangeLimiter = SwedishHolidayDateRangeLimiter(Locale.getDefault())
    }

    @Test
    fun setToNearestDate() {

        // Note: Month indices start at 0, i.e. 8 = September
        mapOf(
            date(2019, 8, 14) to date(2019, 8, 16),
            date(2019, 8, 15) to date(2019, 8, 16),
            date(2019, 11, 24) to date(2019, 11, 27),
            date(2020, 1, 29) to date(2020, 2, 2),
            date(2023, 11, 30) to date(2024, 0, 2)
        ).forEach { (input, expectedOutput) ->
            val output = swedishHolidayDateRangeLimiter.setToNearestDate(input)
            assertThat(output).isEqualTo(expectedOutput)
        }
    }
}
