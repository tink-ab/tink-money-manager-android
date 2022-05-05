---
title: com.tink.model.relations
---
//[moneymanager-ui](../../index.html)/[com.tink.model.relations](index.html)



# Package com.tink.model.relations



## Types


| Name | Summary |
|---|---|
| [AmountByCategory](-amount-by-category/index.html) | [androidJvm]<br>data class [AmountByCategory](-amount-by-category/index.html)(val categoryCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val amount: [Amount](../com.tink.model.misc/-amount/index.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |
| [CommonTransactionsOverview](-common-transactions-overview/index.html) | [androidJvm]<br>data class [CommonTransactionsOverview](-common-transactions-overview/index.html)(val mostCommonTransactionDescription: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val totalNumberOfTransactions: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val mostCommonTransactionCount: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |
| [ExpensesByDay](-expenses-by-day/index.html) | [androidJvm]<br>data class [ExpensesByDay](-expenses-by-day/index.html)(val date: [LocalDate](https://developer.android.com/reference/kotlin/java/time/LocalDate.html), val totalAmount: [Amount](../com.tink.model.misc/-amount/index.html), val averageAmount: [Amount](../com.tink.model.misc/-amount/index.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |
| [LargestExpense](-largest-expense/index.html) | [androidJvm]<br>data class [LargestExpense](-largest-expense/index.html)(val date: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), val amount: [Amount](../com.tink.model.misc/-amount/index.html), val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |
| [TransactionSummary](-transaction-summary/index.html) | [androidJvm]<br>data class [TransactionSummary](-transaction-summary/index.html)(val commonTransactionsOverview: [CommonTransactionsOverview](-common-transactions-overview/index.html), val totalExpenses: [Amount](../com.tink.model.misc/-amount/index.html), val largestExpense: [LargestExpense](-largest-expense/index.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |

