package se.tink.commons.businessdays

import org.junit.jupiter.api.Test

internal class SwedishHolidayTests {

    private fun isHoliday(year: Int, month: Int, day: Int) =
        DateComponents(
            year,
            month,
            day
        ).let { it.isSwedishHoliday() || it.isDeFactoSwedishHoliday() }

    @Test
    fun testChristmasEve() {
        assert(isHoliday(2019, 12, 24))
    }

    @Test
    fun testChristmasDay() {
        assert(isHoliday(2019, 12, 25))
    }

    @Test
    fun testGoodFriday2019() {
        assert(isHoliday(2019, 4, 19))
    }

    @Test
    fun testGoodFriday2020() {
        assert(isHoliday(2020, 4, 10))
    }

    @Test
    fun testNationalDay() {
        assert(isHoliday(2020, 6, 6))
    }

    @Test
    fun testMidsummerEve2020() {
        assert(isHoliday(2020, 6, 19))
    }

    @Test
    fun testMidsummerEve2021() {
        assert(isHoliday(2021, 6, 25))
    }

    @Test
    fun testMidsummerEve2022() {
        assert(isHoliday(2022, 6, 24))
    }

    @Test
    fun testAllSaintsDay() {
        assert(isHoliday(2019, 11, 2))
    }

    @Test
    fun testEpiphany() {
        assert(isHoliday(2020, 1, 6))
    }

    @Test
    fun testEasterMonday2020() {
        assert(isHoliday(2020, 4, 13))
    }

    @Test
    fun testEasterMonday2021() {
        assert(isHoliday(2021, 4, 5))
    }

    @Test
    fun testEasterMonday2022() {
        assert(isHoliday(2022, 4, 18))
    }

    @Test
    fun testEasterMonday2026() {
        assert(isHoliday(2026, 4, 6))
    }

    @Test
    fun testAscensionDay() {
        assert(isHoliday(2020, 5, 21))
    }

    @Test
    fun testWeekends() {
        listOf(
            DateComponents(2019, 9, 14),
            DateComponents(2019, 9, 15),
            DateComponents(2019, 11, 9),
            DateComponents(2019, 11, 24),
            DateComponents(2019, 12, 1),
            DateComponents(2020, 2, 16),
            DateComponents(2020, 6, 21),
            DateComponents(2023, 5, 7),
            DateComponents(2032, 8, 29),
            DateComponents(2032, 8, 28),
            DateComponents(2010, 3, 20),
            DateComponents(1860, 7, 14),
            DateComponents(1860, 7, 15)
        ).forEach {
            assert(it.isWeekend()) { "$it should be on a weekend" }
        }
    }

    @Test
    fun testBusinessDays() {
        listOf(
            DateComponents(2019, 9, 16),
            DateComponents(2019, 9, 20),
            DateComponents(2019, 11, 15),
            DateComponents(2019, 11, 1),
            DateComponents(2019, 12, 4),
            DateComponents(2020, 3, 16),
            DateComponents(2021, 6, 21),
            DateComponents(2023, 5, 5),
            DateComponents(2024, 5, 7),
            DateComponents(2032, 8, 27),
            DateComponents(2034, 8, 28),
            DateComponents(2010, 3, 18),
            DateComponents(1750, 7, 14),
            DateComponents(1860, 7, 30)
        ).forEach {
            assert(
                !it.isWeekend() &&
                    !it.isSwedishHoliday() &&
                    !it.isDeFactoSwedishHoliday()
            ) { "$it should be a business day" }
        }
    }
}
