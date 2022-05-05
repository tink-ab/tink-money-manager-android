---
title: TransactionSummary
---
//[moneymanager-ui](../../../index.html)/[com.tink.model.relations](../index.html)/[TransactionSummary](index.html)



# TransactionSummary



[androidJvm]\
data class [TransactionSummary](index.html)(val commonTransactionsOverview: [CommonTransactionsOverview](../-common-transactions-overview/index.html), val totalExpenses: [Amount](../../com.tink.model.misc/-amount/index.html), val largestExpense: [LargestExpense](../-largest-expense/index.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)



## Constructors


| | |
|---|---|
| [TransactionSummary](-transaction-summary.html) | [androidJvm]<br>fun [TransactionSummary](-transaction-summary.html)(commonTransactionsOverview: [CommonTransactionsOverview](../-common-transactions-overview/index.html), totalExpenses: [Amount](../../com.tink.model.misc/-amount/index.html), largestExpense: [LargestExpense](../-largest-expense/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [describeContents](../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [describeContents](../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [writeToParcel](../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458)(p0: [Parcel](https://developer.android.com/reference/kotlin/android/os/Parcel.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [commonTransactionsOverview](common-transactions-overview.html) | [androidJvm]<br>val [commonTransactionsOverview](common-transactions-overview.html): [CommonTransactionsOverview](../-common-transactions-overview/index.html) |
| [largestExpense](largest-expense.html) | [androidJvm]<br>val [largestExpense](largest-expense.html): [LargestExpense](../-largest-expense/index.html) |
| [totalExpenses](total-expenses.html) | [androidJvm]<br>val [totalExpenses](total-expenses.html): [Amount](../../com.tink.model.misc/-amount/index.html) |

