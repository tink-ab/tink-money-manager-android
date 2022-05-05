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
| [createBudget](create-budget.html) | [androidJvm]<br>abstract fun [createBudget](create-budget.html)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, amount: [Amount](../../com.tink.model.misc/-amount/index.html)? = null, filter: [BudgetFilter](../../com.tink.model.budget/index.html#-2018963458%2FClasslikes%2F1000845458)? = null, periodicity: [BudgetPeriodicity](../../com.tink.model.budget/index.html#-756637127%2FClasslikes%2F1000845458)? = null) |
| [showAccountDetails](show-account-details.html) | [androidJvm]<br>abstract fun [showAccountDetails](show-account-details.html)(accountId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [showBudget](show-budget.html) | [androidJvm]<br>abstract fun [showBudget](show-budget.html)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), periodStart: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [showTransactionDetails](show-transaction-details.html) | [androidJvm]<br>abstract fun [showTransactionDetails](show-transaction-details.html)(transactionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [showTransactionListForIds](show-transaction-list-for-ids.html) | [androidJvm]<br>abstract fun [showTransactionListForIds](show-transaction-list-for-ids.html)(transactionIds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) |

