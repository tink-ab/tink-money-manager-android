---
title: SwedishHolidayDateRangeLimiter
---
//[moneymanager-ui](../../../index.html)/[se.tink.commons.businessdays](../index.html)/[SwedishHolidayDateRangeLimiter](index.html)



# SwedishHolidayDateRangeLimiter



[androidJvm]\
data class [SwedishHolidayDateRangeLimiter](index.html)@Injectconstructor(val locale: [Locale](https://developer.android.com/reference/kotlin/java/util/Locale.html)) : [DateRangeLimiter](../-date-range-limiter/index.html)



## Constructors


| | |
|---|---|
| [SwedishHolidayDateRangeLimiter](-swedish-holiday-date-range-limiter.html) | [androidJvm]<br>@Inject<br>fun [SwedishHolidayDateRangeLimiter](-swedish-holiday-date-range-limiter.html)(locale: [Locale](https://developer.android.com/reference/kotlin/java/util/Locale.html)) |


## Functions


| Name | Summary |
|---|---|
| [describeContents](../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [describeContents](../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [isOutOfRange](is-out-of-range.html) | [androidJvm]<br>open override fun [isOutOfRange](is-out-of-range.html)(year: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), month: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), day: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [setToNearestDate](set-to-nearest-date.html) | [androidJvm]<br>open override fun [setToNearestDate](set-to-nearest-date.html)(day: [Calendar](https://developer.android.com/reference/kotlin/java/util/Calendar.html)): [Calendar](https://developer.android.com/reference/kotlin/java/util/Calendar.html) |
| [writeToParcel](../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [writeToParcel](../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458)(p0: [Parcel](https://developer.android.com/reference/kotlin/android/os/Parcel.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [endDate](end-date.html) | [androidJvm]<br>open override val [endDate](end-date.html): [Calendar](https://developer.android.com/reference/kotlin/java/util/Calendar.html) |
| [locale](locale.html) | [androidJvm]<br>val [locale](locale.html): [Locale](https://developer.android.com/reference/kotlin/java/util/Locale.html) |
| [maxYear](max-year.html) | [androidJvm]<br>open override val [maxYear](max-year.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [minYear](min-year.html) | [androidJvm]<br>open override val [minYear](min-year.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [startDate](start-date.html) | [androidJvm]<br>open override val [startDate](start-date.html): [Calendar](https://developer.android.com/reference/kotlin/java/util/Calendar.html) |

