---
title: DateUtils
---
//[moneymanager-ui](../../../index.html)/[se.tink.utils](../index.html)/[DateUtils](index.html)



# DateUtils



[androidJvm]\
class [DateUtils](index.html)

Uses dates of the class LocalDateTime with a ZoneId for the current user to calcluate the time in the users time zone.



## Constructors


| | |
|---|---|
| [DateUtils](-date-utils.html) | [androidJvm]<br>fun [DateUtils](-date-utils.html)() |


## Types


| Name | Summary |
|---|---|
| [Companion](-companion/index.html) | [androidJvm]<br>object [Companion](-companion/index.html) |


## Functions


| Name | Summary |
|---|---|
| [formatDateHuman](format-date-human.html) | [androidJvm]<br>fun [formatDateHuman](format-date-human.html)(date: [LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>fun [formatDateHuman](format-date-human.html)(date: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [formatDateHumanShort](format-date-human-short.html) | [androidJvm]<br>fun [formatDateHumanShort](format-date-human-short.html)(date: [LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [formatDateRange](format-date-range.html) | [androidJvm]<br>fun [formatDateRange](format-date-range.html)(start: [LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html), end: [LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html), includeYearIfNotCurrent: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [formatYearly](format-yearly.html) | [androidJvm]<br>fun [formatYearly](format-yearly.html)(date: [LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getDateTimeFromPeriod](get-date-time-from-period.html) | [androidJvm]<br>fun [getDateTimeFromPeriod](get-date-time-from-period.html)(period: [Period](../../com.tink.model.time/-period/index.html)): [LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html) |
| [getDateWithYear](get-date-with-year.html) | [androidJvm]<br>fun [getDateWithYear](get-date-with-year.html)(dateTime: [LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getDayOfMonth](get-day-of-month.html) | [androidJvm]<br>fun [getDayOfMonth](get-day-of-month.html)(dateTime: [LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getDayOfWeek](get-day-of-week.html) | [androidJvm]<br>fun [getDayOfWeek](get-day-of-week.html)(dateTime: [LocalDate](https://developer.android.com/reference/kotlin/java/time/LocalDate.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getMonthAndYearFromDateTime](get-month-and-year-from-date-time.html) | [androidJvm]<br>fun [getMonthAndYearFromDateTime](get-month-and-year-from-date-time.html)(date: [LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getMonthCompact](get-month-compact.html) | [androidJvm]<br>fun [getMonthCompact](get-month-compact.html)(dateTime: [LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getMonthNameAndMaybeYearOfPeriod](get-month-name-and-maybe-year-of-period.html) | [androidJvm]<br>fun [getMonthNameAndMaybeYearOfPeriod](get-month-name-and-maybe-year-of-period.html)(period: [Period](../../com.tink.model.time/-period/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getMonthNameOfDate](get-month-name-of-date.html) | [androidJvm]<br>fun [getMonthNameOfDate](get-month-name-of-date.html)(date: [LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html), includeYearIfNotCurrent: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getMonthNameOfPeriod](get-month-name-of-period.html) | [androidJvm]<br>fun [getMonthNameOfPeriod](get-month-name-of-period.html)(period: [Period](../../com.tink.model.time/-period/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getMonthWithDayOfMonth](get-month-with-day-of-month.html) | [androidJvm]<br>fun [getMonthWithDayOfMonth](get-month-with-day-of-month.html)(dateTime: [LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [isCurrentYear](is-current-year.html) | [androidJvm]<br>fun [isCurrentYear](is-current-year.html)(evaluatedDate: [LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isInPastDays](is-in-past-days.html) | [androidJvm]<br>fun [isInPastDays](is-in-past-days.html)(evaluatedDate: [LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html), numberOfDaysToInclude: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isToday](is-today.html) | [androidJvm]<br>fun [isToday](is-today.html)(evaluatedDate: [LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isTomorrow](is-tomorrow.html) | [androidJvm]<br>fun [isTomorrow](is-tomorrow.html)(evaluatedDate: [LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isYesterday](is-yesterday.html) | [androidJvm]<br>fun [isYesterday](is-yesterday.html)(evaluatedDate: [LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |


## Properties


| Name | Summary |
|---|---|
| [formatHumanStrings](format-human-strings.html) | [androidJvm]<br>var [formatHumanStrings](format-human-strings.html): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [locale](locale.html) | [androidJvm]<br>var [locale](locale.html): [Locale](https://developer.android.com/reference/kotlin/java/util/Locale.html) |
| [timeZoneId](time-zone-id.html) | [androidJvm]<br>var [timeZoneId](time-zone-id.html): [ZoneId](https://developer.android.com/reference/kotlin/java/time/ZoneId.html) |

