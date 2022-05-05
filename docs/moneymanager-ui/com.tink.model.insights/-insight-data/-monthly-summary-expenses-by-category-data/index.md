---
title: MonthlySummaryExpensesByCategoryData
---
//[moneymanager-ui](../../../../index.html)/[com.tink.model.insights](../../index.html)/[InsightData](../index.html)/[MonthlySummaryExpensesByCategoryData](index.html)



# MonthlySummaryExpensesByCategoryData



[androidJvm]\
data class [MonthlySummaryExpensesByCategoryData](index.html)(val month: [YearMonth](../../../com.tink.model.time/-year-month/index.html), val expenses: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[AmountByCategory](../../../com.tink.model.relations/-amount-by-category/index.html)&gt;) : [InsightData](../index.html)



## Constructors


| | |
|---|---|
| [MonthlySummaryExpensesByCategoryData](-monthly-summary-expenses-by-category-data.html) | [androidJvm]<br>fun [MonthlySummaryExpensesByCategoryData](-monthly-summary-expenses-by-category-data.html)(month: [YearMonth](../../../com.tink.model.time/-year-month/index.html), expenses: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[AmountByCategory](../../../com.tink.model.relations/-amount-by-category/index.html)&gt;) |


## Functions


| Name | Summary |
|---|---|
| [describeContents](../../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [describeContents](../../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](../../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [writeToParcel](../../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458)(p0: [Parcel](https://developer.android.com/reference/kotlin/android/os/Parcel.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [expenses](expenses.html) | [androidJvm]<br>val [expenses](expenses.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[AmountByCategory](../../../com.tink.model.relations/-amount-by-category/index.html)&gt; |
| [month](month.html) | [androidJvm]<br>val [month](month.html): [YearMonth](../../../com.tink.model.time/-year-month/index.html) |

