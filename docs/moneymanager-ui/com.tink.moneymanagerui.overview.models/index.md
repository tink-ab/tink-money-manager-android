---
title: com.tink.moneymanagerui.overview.models
---
//[moneymanager-ui](../../index.html)/[com.tink.moneymanagerui.overview.models](index.html)



# Package com.tink.moneymanagerui.overview.models



## Types


| Name | Summary |
|---|---|
| [OverviewAccountsUIModel](-overview-accounts-u-i-model/index.html) | [androidJvm]<br>data class [OverviewAccountsUIModel](-overview-accounts-u-i-model/index.html)(var overviewAccounts: ResponseState&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[AccountWithImage](../com.tink.moneymanagerui.accounts/-account-with-image/index.html)&gt;&gt; = LoadingState) |
| [OverviewInsightsUIModel](-overview-insights-u-i-model/index.html) | [androidJvm]<br>data class [OverviewInsightsUIModel](-overview-insights-u-i-model/index.html)(var insights: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Insight&gt; = emptyList()) |
| [OverviewTransactionsUIModel](-overview-transactions-u-i-model/index.html) | [androidJvm]<br>data class [OverviewTransactionsUIModel](-overview-transactions-u-i-model/index.html)(var catResponseState: ResponseState&lt;CategoryTree&gt; = LoadingState, var trxItemList: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ListItem.TransactionItem](../se.tink.commons.transactions/-list-item/-transaction-item/index.html)&gt; = emptyList(), var isLoading: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, var isTransactionDetailsEnabled: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true) |

