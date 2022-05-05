---
title: com.tink.model.time
---
//[moneymanager-ui](../../index.html)/[com.tink.model.time](index.html)



# Package com.tink.model.time



## Types


| Name | Summary |
|---|---|
| [DayPeriod](-day-period/index.html) | [androidJvm]<br>data class [DayPeriod](-day-period/index.html)(val dayOfMonth: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val monthOfYear: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val year: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val identifier: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val start: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html), val end: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html)) : [Period](-period/index.html) |
| [MonthPeriod](-month-period/index.html) | [androidJvm]<br>data class [MonthPeriod](-month-period/index.html)(val monthOfYear: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val year: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val identifier: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val start: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html), val end: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html)) : [Period](-period/index.html) |
| [Period](-period/index.html) | [androidJvm]<br>sealed class [Period](-period/index.html) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |
| [WeekPeriod](-week-period/index.html) | [androidJvm]<br>data class [WeekPeriod](-week-period/index.html)(val weekOfYear: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val year: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val identifier: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val start: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html), val end: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html)) : [Period](-period/index.html) |
| [YearMonth](-year-month/index.html) | [androidJvm]<br>data class [YearMonth](-year-month/index.html)(val year: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val month: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |
| [YearPeriod](-year-period/index.html) | [androidJvm]<br>data class [YearPeriod](-year-period/index.html)(val year: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val identifier: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val start: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html), val end: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html)) : [Period](-period/index.html) |
| [YearWeek](-year-week/index.html) | [androidJvm]<br>data class [YearWeek](-year-week/index.html)(val year: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val week: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |

