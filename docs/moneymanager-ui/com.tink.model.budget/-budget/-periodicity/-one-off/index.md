---
title: OneOff
---
//[moneymanager-ui](../../../../../index.html)/[com.tink.model.budget](../../../index.html)/[Budget](../../index.html)/[Periodicity](../index.html)/[OneOff](index.html)



# OneOff



[androidJvm]\
data class [OneOff](index.html)(val start: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html), val end: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html)) : [Budget.Periodicity](../index.html)



## Constructors


| | |
|---|---|
| [OneOff](-one-off.html) | [androidJvm]<br>fun [OneOff](-one-off.html)(start: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html), end: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html)) |


## Functions


| Name | Summary |
|---|---|
| [describeContents](../../../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [describeContents](../../../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](../../../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [writeToParcel](../../../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458)(p0: [Parcel](https://developer.android.com/reference/kotlin/android/os/Parcel.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [end](end.html) | [androidJvm]<br>val [end](end.html): [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html) |
| [start](start.html) | [androidJvm]<br>val [start](start.html): [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html) |


## Extensions


| Name | Summary |
|---|---|
| [formattedPeriod](../../../../com.tink.moneymanagerui.extensions/formatted-period.html) | [androidJvm]<br>fun [Budget.Periodicity.OneOff](index.html).[formattedPeriod](../../../../com.tink.moneymanagerui.extensions/formatted-period.html)(dateUtils: [DateUtils](../../../../se.tink.utils/-date-utils/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

