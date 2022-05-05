---
title: Periodicity
---
//[moneymanager-ui](../../../../index.html)/[com.tink.model.budget](../../index.html)/[Budget](../index.html)/[Periodicity](index.html)



# Periodicity



[androidJvm]\
sealed class [Periodicity](index.html) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)



## Types


| Name | Summary |
|---|---|
| [OneOff](-one-off/index.html) | [androidJvm]<br>data class [OneOff](-one-off/index.html)(val start: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html), val end: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html)) : [Budget.Periodicity](index.html) |
| [Recurring](-recurring/index.html) | [androidJvm]<br>data class [Recurring](-recurring/index.html)(val unit: [Budget.Periodicity.Recurring.PeriodUnit](-recurring/-period-unit/index.html)) : [Budget.Periodicity](index.html) |


## Functions


| Name | Summary |
|---|---|
| [describeContents](../../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [describeContents](../../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](../../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [writeToParcel](../../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458)(p0: [Parcel](https://developer.android.com/reference/kotlin/android/os/Parcel.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |


## Inheritors


| Name |
|---|
| [OneOff](-one-off/index.html) |
| [Recurring](-recurring/index.html) |


## Extensions


| Name | Summary |
|---|---|
| [toPeriodValue](../../../com.tink.moneymanagerui.budgets.creation.specification/to-period-value.html) | [androidJvm]<br>fun [Budget.Periodicity](index.html).[toPeriodValue](../../../com.tink.moneymanagerui.budgets.creation.specification/to-period-value.html)(): [PeriodValue](../../../com.tink.moneymanagerui.budgets.creation.specification/-period-value/index.html) |

