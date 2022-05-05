---
title: Period
---
//[moneymanager-ui](../../../index.html)/[com.tink.model.time](../index.html)/[Period](index.html)



# Period



[androidJvm]\
sealed class [Period](index.html) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)



## Functions


| Name | Summary |
|---|---|
| [describeContents](../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [describeContents](../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [writeToParcel](../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458)(p0: [Parcel](https://developer.android.com/reference/kotlin/android/os/Parcel.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [end](end.html) | [androidJvm]<br>abstract val [end](end.html): [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html) |
| [identifier](identifier.html) | [androidJvm]<br>abstract val [identifier](identifier.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [start](start.html) | [androidJvm]<br>abstract val [start](start.html): [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html) |


## Inheritors


| Name |
|---|
| [YearPeriod](../-year-period/index.html) |
| [MonthPeriod](../-month-period/index.html) |
| [DayPeriod](../-day-period/index.html) |
| [WeekPeriod](../-week-period/index.html) |


## Extensions


| Name | Summary |
|---|---|
| [getHalfwayPoint](../../com.tink.moneymanagerui.extensions/get-halfway-point.html) | [androidJvm]<br>fun [Period](index.html).[getHalfwayPoint](../../com.tink.moneymanagerui.extensions/get-halfway-point.html)(): DateTime |
| [isInPeriod](../../se.tink.commons.extensions/is-in-period.html) | [androidJvm]<br>fun [Period](index.html).[isInPeriod](../../se.tink.commons.extensions/is-in-period.html)(dateTime: DateTime): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

