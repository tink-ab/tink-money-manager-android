---
title: InsightState
---
//[moneymanager-ui](../../../index.html)/[com.tink.model.insights](../index.html)/[InsightState](index.html)



# InsightState



[androidJvm]\
sealed class [InsightState](index.html) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)



## Types


| Name | Summary |
|---|---|
| [Active](-active/index.html) | [androidJvm]<br>object [Active](-active/index.html) : [InsightState](index.html) |
| [Archived](-archived/index.html) | [androidJvm]<br>data class [Archived](-archived/index.html)(val archivedDate: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html)) : [InsightState](index.html) |


## Functions


| Name | Summary |
|---|---|
| [describeContents](../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [describeContents](../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [writeToParcel](../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458)(p0: [Parcel](https://developer.android.com/reference/kotlin/android/os/Parcel.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |


## Inheritors


| Name |
|---|
| [Archived](-archived/index.html) |
| [Active](-active/index.html) |

