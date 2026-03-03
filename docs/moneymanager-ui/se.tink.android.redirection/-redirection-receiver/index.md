---
title: RedirectionReceiver
---
//[moneymanager-ui](../../../index.html)/[se.tink.android.redirection](../index.html)/[RedirectionReceiver](index.html)



# RedirectionReceiver



[androidJvm]\
interface [RedirectionReceiver](index.html)



## Functions


| Name | Summary |
|---|---|
| [categorizeTransaction](categorize-transaction.html) | [androidJvm]<br>abstract fun [categorizeTransaction](categorize-transaction.html)(transactionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [createBudget](create-budget.html) | [androidJvm]<br>abstract fun [createBudget](create-budget.html)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, amount: Amount? = null, filter: BudgetFilter? = null, periodicity: BudgetPeriodicity? = null) |
| [showAccountDetails](show-account-details.html) | [androidJvm]<br>abstract fun [showAccountDetails](show-account-details.html)(accountId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [showBudget](show-budget.html) | [androidJvm]<br>abstract fun [showBudget](show-budget.html)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), periodStart: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [showLeftToSpendMonthly](show-left-to-spend-monthly.html) | [androidJvm]<br>abstract fun [showLeftToSpendMonthly](show-left-to-spend-monthly.html)(month: YearMonth) |
| [showTransactionDetails](show-transaction-details.html) | [androidJvm]<br>abstract fun [showTransactionDetails](show-transaction-details.html)(transactionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [showTransactionListForIds](show-transaction-list-for-ids.html) | [androidJvm]<br>abstract fun [showTransactionListForIds](show-transaction-list-for-ids.html)(transactionIds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) |

