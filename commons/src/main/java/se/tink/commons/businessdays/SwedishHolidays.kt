package se.tink.commons.businessdays


/// Gauss' Easter algorithm
///
/// - Note: [Gauss' Easter algorithm](https://en.wikipedia.org/wiki/Computus#Gauss'_Easter_algorithm)
private fun dateComponentsOfEasterSunday(year: Int): DateComponents {
    val a = year % 19
    val b = year % 4
    val c = year % 7
    val k = year / 100
    val p = (13 + 8 * k) / 25
    val q = k / 4
    val M = (15 - p + k - q) % 30
    val N = (4 + k - q) % 7
    val d = (19 * a + M) % 30
    val e = (2 * b + 4 * c + 6 * d + N) % 7

    val dayInMarch = 22 + d + e
    val dayInApril = d + e - 9

    return when {
        dayInMarch <= 31 -> DateComponents(year = year, month = 3, day = dayInMarch)
        d == 29 && e == 6 && dayInApril == 25 -> DateComponents(year = year, month = 4, day = 19)
        d == 28 && e == 6 && (11 * M + 11) % 30 < 19 && dayInApril == 25 -> DateComponents(
            year = year,
            month = 4,
            day = 18
        )
        else -> DateComponents(year = year, month = 4, day = dayInApril)
    }
}

private fun dateComponentsOfGoodFriday(year: Int): DateComponents {
    val easter = dateComponentsOfEasterSunday(year = year).toDateTime()
    return easter.minusDays(2).toDateComponents()
}

private fun dateComponentsOfEasterMonday(year: Int): DateComponents {
    val easter = dateComponentsOfEasterSunday(year = year).toDateTime()
    return easter.plusDays(1).toDateComponents()
}

private fun dateComponentsOfAscensionDay(year: Int): DateComponents {
    val easter = dateComponentsOfEasterSunday(year = year).toDateTime()
    return easter.plusDays(39).toDateComponents()
}

private fun dateComponentsOfPentecost(year: Int): DateComponents {
    val easter = dateComponentsOfEasterSunday(year = year).toDateTime()
    return easter.plusDays(49).toDateComponents()
}

/// Easter Sunday
/// - Date: The first Sunday after a full moon on or after March 21
/// - Note: Påskdagen
fun DateComponents.isEasterSunday() = this == dateComponentsOfEasterSunday(year)

/// Good Friday
/// - Date: The Friday before Easter Sunday
/// - Note: Långfredagen
fun DateComponents.isGoodFriday() = this == dateComponentsOfGoodFriday(year)

/// Easter Monday
/// - Date: Monday after Easter Sunday (one day after Easter Sunday)
/// - Note: Annandag påsk
fun DateComponents.isEasterMonday() = this == dateComponentsOfEasterMonday(year)

/// Ascension Day
/// - Date: 39 days after Easter Sunday
/// - Note: Kristi himmelsfärds dag
fun DateComponents.isAscensionDay() = this == dateComponentsOfAscensionDay(year)

/// Pentecost
/// - Date: The 7th Sunday (49 days) after Easter Sunday
/// - Note: Pingstdagen
fun DateComponents.isPentecost() = this == dateComponentsOfPentecost(year)

/// New Year's Day
///
/// - Date: 1 January
/// - Note: Nyårsdagen
fun DateComponents.isNewYearsDay() = this == DateComponents(year = year, month = 1, day = 1)

/// Epiphany
/// - Date: 6 January
/// - Note: Trettondedag jul
fun DateComponents.isEpiphany() = this == DateComponents(year = year, month = 1, day = 6)

/// International Workers' Day
/// - Date: 1 May
/// - Note: Första Maj
fun DateComponents.isInternationalWorkersDay() =
    this == DateComponents(year = year, month = 5, day = 1)

/// National Day of Sweden
/// - Date: 6 June
/// - Note: Sveriges nationaldag
fun DateComponents.isNationalDayOfSweden() = this == DateComponents(year = year, month = 6, day = 6)

/// Midsummer's Eve
/// - Date: The Friday during the period 19–25 June. (2016: June 24)
/// - Note: Midsommarafton
fun DateComponents.isMidsummersEve(): Boolean {
    val dateTime = toDateTime().plusHours(1)

    val intervalStart = DateComponents(year, month = 6, day = 19).toDateTime()
    val intervalEnd = DateComponents(year, month = 6, day = 26).toDateTime() //Exclusive

    return dateTime.dayOfWeek == 5
            && dateTime.isAfter(intervalStart)
            && dateTime.isBefore(intervalEnd)
}

/// Midsummer's Day
/// - Date: The Saturday during the period 20–26 June
/// - Note: Midsommardagen
fun DateComponents.isMidsummersDay(): Boolean {
    val dateTime = toDateTime().plusHours(1)

    val intervalStart = DateComponents(year, month = 6, day = 20).toDateTime()
    val intervalEnd = DateComponents(year, month = 6, day = 27).toDateTime() //Exclusive

    return dateTime.dayOfWeek == 6
            && dateTime.isAfter(intervalStart)
            && dateTime.isBefore(intervalEnd)
}

/// All Saints' Day
/// - Date: The Saturday during the period 31 October–6 November
/// - Note: Alla helgons dag
fun DateComponents.isAllSaintsDay(): Boolean {
    val dateTime = toDateTime().plusHours(1)

    val intervalStart = DateComponents(year, month = 10, day = 31).toDateTime()
    val intervalEnd = DateComponents(year, month = 11, day = 7).toDateTime() //Exclusive

    return dateTime.dayOfWeek == 6
            && dateTime.isAfter(intervalStart)
            && dateTime.isBefore(intervalEnd)
}

/// Christmas Eve
///
/// - Date: 24 December
/// - Note: Julafton
fun DateComponents.isChristmasEve() = this == DateComponents(year = year, month = 12, day = 24)

/// Christmas Day
///
/// - Date: 25 December
/// - Note: Juldagen
fun DateComponents.isChristmasDay() = this == DateComponents(year = year, month = 12, day = 25)

/// Second Day of Christmas
///
/// - Date: 26 December
/// - Note: Annandag jul
fun DateComponents.isSecondDayOfChristmas() =
    this == DateComponents(year = year, month = 12, day = 26)

/// New Year's Eve
///
/// - Date: 31 December
/// - Note: Nyårsafton
fun DateComponents.isNewYearsEve() = this == DateComponents(year = year, month = 12, day = 31)
