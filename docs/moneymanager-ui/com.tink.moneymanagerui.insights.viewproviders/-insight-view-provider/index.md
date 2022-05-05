---
title: InsightViewProvider
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.insights.viewproviders](../index.html)/[InsightViewProvider](index.html)



# InsightViewProvider



[androidJvm]\
interface [InsightViewProvider](index.html)



## Functions


| Name | Summary |
|---|---|
| [getDataHolder](get-data-holder.html) | [androidJvm]<br>abstract fun [getDataHolder](get-data-holder.html)(insight: [Insight](../../com.tink.model.insights/-insight/index.html)): [InsightDataHolder](../-insight-data-holder/index.html) |
| [viewHolder](view-holder.html) | [androidJvm]<br>abstract fun [viewHolder](view-holder.html)(parent: [ViewGroup](https://developer.android.com/reference/kotlin/android/view/ViewGroup.html), actionHandler: [ActionHandler](../../com.tink.moneymanagerui.insights.actionhandling/-action-handler/index.html)): [InsightViewHolder](../-insight-view-holder/index.html) |


## Properties


| Name | Summary |
|---|---|
| [supportedInsightTypes](supported-insight-types.html) | [androidJvm]<br>abstract val [supportedInsightTypes](supported-insight-types.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[InsightType](../../com.tink.model.insights/-insight-type/index.html)&gt; |
| [viewType](view-type.html) | [androidJvm]<br>abstract val [viewType](view-type.html): &lt;ERROR CLASS&gt; |


## Inheritors


| Name |
|---|
| [BudgetCreateSuggestionViewProvider](../-budget-create-suggestion-view-provider/index.html) |
| [BudgetMonthlySummaryViewProvider](../-budget-monthly-summary-view-provider/index.html) |
| [BudgetStateViewProvider](../-budget-state-view-provider/index.html) |
| [ExpensesByCategoryViewProvider](../-expenses-by-category-view-provider/index.html) |
| [IconTextViewProvider](../-icon-text-view-provider/index.html) |
| [TransactionsSummaryViewProvider](../-transactions-summary-view-provider/index.html) |
| [UncategorizedExpenseViewProvider](../-uncategorized-expense-view-provider/index.html) |
| [WeeklyExpensesByDayViewProvider](../-weekly-expenses-by-day-view-provider/index.html) |

