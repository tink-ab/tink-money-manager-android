---
title: BudgetSummaryOverspentData
---
//[moneymanager-ui](../../../../index.html)/[com.tink.model.insights](../../index.html)/[InsightData](../index.html)/[BudgetSummaryOverspentData](index.html)



# BudgetSummaryOverspentData



[androidJvm]\
data class [BudgetSummaryOverspentData](index.html)(val achievedBudgets: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[InsightData.BudgetIdToPeriod](../-budget-id-to-period/index.html)&gt;, val overspentBudgets: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[InsightData.BudgetIdToPeriod](../-budget-id-to-period/index.html)&gt;, val overspentAmount: [Amount](../../../com.tink.model.misc/-amount/index.html)) : [InsightData](../index.html)



## Constructors


| | |
|---|---|
| [BudgetSummaryOverspentData](-budget-summary-overspent-data.html) | [androidJvm]<br>fun [BudgetSummaryOverspentData](-budget-summary-overspent-data.html)(achievedBudgets: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[InsightData.BudgetIdToPeriod](../-budget-id-to-period/index.html)&gt;, overspentBudgets: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[InsightData.BudgetIdToPeriod](../-budget-id-to-period/index.html)&gt;, overspentAmount: [Amount](../../../com.tink.model.misc/-amount/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [describeContents](../../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [describeContents](../../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](../../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [writeToParcel](../../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458)(p0: [Parcel](https://developer.android.com/reference/kotlin/android/os/Parcel.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [achievedBudgets](achieved-budgets.html) | [androidJvm]<br>val [achievedBudgets](achieved-budgets.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[InsightData.BudgetIdToPeriod](../-budget-id-to-period/index.html)&gt; |
| [overspentAmount](overspent-amount.html) | [androidJvm]<br>val [overspentAmount](overspent-amount.html): [Amount](../../../com.tink.model.misc/-amount/index.html) |
| [overspentBudgets](overspent-budgets.html) | [androidJvm]<br>val [overspentBudgets](overspent-budgets.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[InsightData.BudgetIdToPeriod](../-budget-id-to-period/index.html)&gt; |

