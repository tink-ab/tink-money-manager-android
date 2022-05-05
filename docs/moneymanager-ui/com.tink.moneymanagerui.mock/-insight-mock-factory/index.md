---
title: InsightMockFactory
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.mock](../index.html)/[InsightMockFactory](index.html)



# InsightMockFactory



[androidJvm]\
object [InsightMockFactory](index.html)



## Functions


| Name | Summary |
|---|---|
| [getAccountBalanceLow](get-account-balance-low.html) | [androidJvm]<br>fun [getAccountBalanceLow](get-account-balance-low.html)(action: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = getAcknowledgeAction()): [JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html) |
| [getAcknowledgeAction](get-acknowledge-action.html) | [androidJvm]<br>fun [getAcknowledgeAction](get-acknowledge-action.html)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getAggregateRefreshP2d2Credentials](get-aggregate-refresh-p2d2-credentials.html) | [androidJvm]<br>fun [getAggregateRefreshP2d2Credentials](get-aggregate-refresh-p2d2-credentials.html)(refreshCredentials: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;123&quot;): [JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html) |
| [getBudgetSuggestCreateFirst](get-budget-suggest-create-first.html) | [androidJvm]<br>fun [getBudgetSuggestCreateFirst](get-budget-suggest-create-first.html)(): [JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html) |
| [getBudgetSuggestCreateTopCategory](get-budget-suggest-create-top-category.html) | [androidJvm]<br>fun [getBudgetSuggestCreateTopCategory](get-budget-suggest-create-top-category.html)(): [JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html) |
| [getBudgetSuggestCreateTopPrimaryCategory](get-budget-suggest-create-top-primary-category.html) | [androidJvm]<br>fun [getBudgetSuggestCreateTopPrimaryCategory](get-budget-suggest-create-top-primary-category.html)(): [JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html) |
| [getCreateBudgetAction](get-create-budget-action.html) | [androidJvm]<br>fun [getCreateBudgetAction](get-create-budget-action.html)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getCreditCardLimitClose](get-credit-card-limit-close.html) | [androidJvm]<br>fun [getCreditCardLimitClose](get-credit-card-limit-close.html)(): [JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html) |
| [getCreditCardLimitReached](get-credit-card-limit-reached.html) | [androidJvm]<br>fun [getCreditCardLimitReached](get-credit-card-limit-reached.html)(): [JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html) |
| [getDefaultInsight](get-default-insight.html) | [androidJvm]<br>fun [getDefaultInsight](get-default-insight.html)(type: [InsightType](../../com.tink.model.insights/-insight-type/index.html)): [JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html) |
| [getDismissAction](get-dismiss-action.html) | [androidJvm]<br>fun [getDismissAction](get-dismiss-action.html)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getDoubleCharge](get-double-charge.html) | [androidJvm]<br>fun [getDoubleCharge](get-double-charge.html)(transactionIds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; = listOf()): [JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html) |
| [getInsightForType](get-insight-for-type.html) | [androidJvm]<br>fun [getInsightForType](get-insight-for-type.html)(type: [InsightType](../../com.tink.model.insights/-insight-type/index.html)): [JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html) |
| [getInsightsForTypes](get-insights-for-types.html) | [androidJvm]<br>fun [getInsightsForTypes](get-insights-for-types.html)(types: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[InsightType](../../com.tink.model.insights/-insight-type/index.html)&gt; = allTypes): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html)&gt; |
| [getLargeExpense](get-large-expense.html) | [androidJvm]<br>fun [getLargeExpense](get-large-expense.html)(transactionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;8a703fa458d144f9b802b09b26a43e89&quot;): [JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html) |
| [getMonthlySummaryExpenseByCategory](get-monthly-summary-expense-by-category.html) | [androidJvm]<br>fun [getMonthlySummaryExpenseByCategory](get-monthly-summary-expense-by-category.html)(): [JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html) |
| [getMonthlySummaryExpensesTransactions](get-monthly-summary-expenses-transactions.html) | [androidJvm]<br>fun [getMonthlySummaryExpensesTransactions](get-monthly-summary-expenses-transactions.html)(): [JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html) |
| [getNewIncomeTransaction](get-new-income-transaction.html) | [androidJvm]<br>fun [getNewIncomeTransaction](get-new-income-transaction.html)(): [JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html) |
| [getRefreshCredentialAction](get-refresh-credential-action.html) | [androidJvm]<br>fun [getRefreshCredentialAction](get-refresh-credential-action.html)(credentialsId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;123&quot;): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getSingleUncategorizedTransaction](get-single-uncategorized-transaction.html) | [androidJvm]<br>fun [getSingleUncategorizedTransaction](get-single-uncategorized-transaction.html)(transactionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;8a703fa458d144f9b802b09b26a43e89&quot;): [JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html) |
| [getViewAccountAction](get-view-account-action.html) | [androidJvm]<br>fun [getViewAccountAction](get-view-account-action.html)(accountId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;d2b49640cbba4d8899a4886b6e8892f8&quot;): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getWeeklySummaryExpensesByCategory](get-weekly-summary-expenses-by-category.html) | [androidJvm]<br>fun [getWeeklySummaryExpensesByCategory](get-weekly-summary-expenses-by-category.html)(): [JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html) |
| [getWeeklySummaryExpensesByDay](get-weekly-summary-expenses-by-day.html) | [androidJvm]<br>fun [getWeeklySummaryExpensesByDay](get-weekly-summary-expenses-by-day.html)(): [JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html) |
| [getWeeklySummaryExpensesTransactions](get-weekly-summary-expenses-transactions.html) | [androidJvm]<br>fun [getWeeklySummaryExpensesTransactions](get-weekly-summary-expenses-transactions.html)(): [JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html) |
| [getWeeklyUncategorizedTransactions](get-weekly-uncategorized-transactions.html) | [androidJvm]<br>fun [getWeeklyUncategorizedTransactions](get-weekly-uncategorized-transactions.html)(transactionIds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; = listOf()): [JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html) |


## Properties


| Name | Summary |
|---|---|
| [allTypes](all-types.html) | [androidJvm]<br>val [allTypes](all-types.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[InsightType](../../com.tink.model.insights/-insight-type/index.html)&gt; |
| [supportedTypes](supported-types.html) | [androidJvm]<br>val [supportedTypes](supported-types.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[InsightType](../../com.tink.model.insights/-insight-type/index.html)&gt; |

