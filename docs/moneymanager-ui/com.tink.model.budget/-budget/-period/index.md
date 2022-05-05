---
title: Period
---
//[moneymanager-ui](../../../../index.html)/[com.tink.model.budget](../../index.html)/[Budget](../index.html)/[Period](index.html)



# Period



[androidJvm]\
data class [Period](index.html)(val start: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html), val end: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html), val spentAmount: [Amount](../../../com.tink.model.misc/-amount/index.html), val budgetAmount: [Amount](../../../com.tink.model.misc/-amount/index.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)



## Constructors


| | |
|---|---|
| [Period](-period.html) | [androidJvm]<br>fun [Period](-period.html)(start: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html), end: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html), spentAmount: [Amount](../../../com.tink.model.misc/-amount/index.html), budgetAmount: [Amount](../../../com.tink.model.misc/-amount/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [describeContents](../../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [describeContents](../../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](../../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [writeToParcel](../../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458)(p0: [Parcel](https://developer.android.com/reference/kotlin/android/os/Parcel.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [budgetAmount](budget-amount.html) | [androidJvm]<br>val [budgetAmount](budget-amount.html): [Amount](../../../com.tink.model.misc/-amount/index.html) |
| [end](end.html) | [androidJvm]<br>val [end](end.html): [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html) |
| [spentAmount](spent-amount.html) | [androidJvm]<br>val [spentAmount](spent-amount.html): [Amount](../../../com.tink.model.misc/-amount/index.html) |
| [start](start.html) | [androidJvm]<br>val [start](start.html): [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html) |


## Extensions


| Name | Summary |
|---|---|
| [getHalfwayPoint](../../../com.tink.moneymanagerui.extensions/get-halfway-point.html) | [androidJvm]<br>fun [Budget.Period](index.html).[getHalfwayPoint](../../../com.tink.moneymanagerui.extensions/get-halfway-point.html)(): DateTime |
| [toHistoricIntervalLabel](../../../com.tink.moneymanagerui.extensions/to-historic-interval-label.html) | [androidJvm]<br>fun [Budget.Period](index.html).[toHistoricIntervalLabel](../../../com.tink.moneymanagerui.extensions/to-historic-interval-label.html)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), dateUtils: [DateUtils](../../../se.tink.utils/-date-utils/index.html), periodicity: [Budget.Periodicity.Recurring](../-periodicity/-recurring/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [toPeriodChartLabel](../../../com.tink.moneymanagerui.extensions/to-period-chart-label.html) | [androidJvm]<br>fun [Budget.Period](index.html).[toPeriodChartLabel](../../../com.tink.moneymanagerui.extensions/to-period-chart-label.html)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), dateUtils: [DateUtils](../../../se.tink.utils/-date-utils/index.html), periodicity: [Budget.Periodicity.Recurring](../-periodicity/-recurring/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

