---
title: Recurring
---
//[moneymanager-ui](../../../../../index.html)/[com.tink.model.budget](../../../index.html)/[Budget](../../index.html)/[Periodicity](../index.html)/[Recurring](index.html)



# Recurring



[androidJvm]\
data class [Recurring](index.html)(val unit: [Budget.Periodicity.Recurring.PeriodUnit](-period-unit/index.html)) : [Budget.Periodicity](../index.html)



## Constructors


| | |
|---|---|
| [Recurring](-recurring.html) | [androidJvm]<br>fun [Recurring](-recurring.html)(unit: [Budget.Periodicity.Recurring.PeriodUnit](-period-unit/index.html)) |


## Types


| Name | Summary |
|---|---|
| [PeriodUnit](-period-unit/index.html) | [androidJvm]<br>enum [PeriodUnit](-period-unit/index.html) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[Budget.Periodicity.Recurring.PeriodUnit](-period-unit/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [describeContents](../../../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [describeContents](../../../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](../../../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [writeToParcel](../../../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458)(p0: [Parcel](https://developer.android.com/reference/kotlin/android/os/Parcel.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [unit](unit.html) | [androidJvm]<br>val [unit](unit.html): [Budget.Periodicity.Recurring.PeriodUnit](-period-unit/index.html) |


## Extensions


| Name | Summary |
|---|---|
| [formattedPeriod](../../../../com.tink.moneymanagerui.extensions/formatted-period.html) | [androidJvm]<br>fun [Budget.Periodicity.Recurring](index.html).[formattedPeriod](../../../../com.tink.moneymanagerui.extensions/formatted-period.html)(budgetPeriod: [BudgetPeriod](../../../index.html#406477269%2FClasslikes%2F1000845458), dateUtils: [DateUtils](../../../../se.tink.utils/-date-utils/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

