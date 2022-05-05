---
title: BudgetMonthlySummaryViewProvider
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.insights.viewproviders](../index.html)/[BudgetMonthlySummaryViewProvider](index.html)



# BudgetMonthlySummaryViewProvider



[androidJvm]\
@ContributesInsightViewProvider



class [BudgetMonthlySummaryViewProvider](index.html)@Injectconstructor : [InsightViewProvider](../-insight-view-provider/index.html)



## Constructors


| | |
|---|---|
| [BudgetMonthlySummaryViewProvider](-budget-monthly-summary-view-provider.html) | [androidJvm]<br>@Inject<br>fun [BudgetMonthlySummaryViewProvider](-budget-monthly-summary-view-provider.html)() |


## Types


| Name | Summary |
|---|---|
| [BudgetColor](-budget-color/index.html) | [androidJvm]<br>sealed class [BudgetColor](-budget-color/index.html) |
| [BudgetMonthlySummaryCategory](-budget-monthly-summary-category/index.html) | [androidJvm]<br>data class [BudgetMonthlySummaryCategory](-budget-monthly-summary-category/index.html)(val icon: [IconResource](../../se.tink.commons.icons/-icon-resource/index.html), val iconColor: [BudgetMonthlySummaryViewProvider.BudgetColor](-budget-color/index.html)) |
| [BudgetMonthlySummaryDataHolder](-budget-monthly-summary-data-holder/index.html) | [androidJvm]<br>data class [BudgetMonthlySummaryDataHolder](-budget-monthly-summary-data-holder/index.html)(val categories: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[BudgetMonthlySummaryViewProvider.BudgetMonthlySummaryCategory](-budget-monthly-summary-category/index.html)&gt;, val progress: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html), val progressChartColor: [BudgetMonthlySummaryViewProvider.BudgetColor](-budget-color/index.html), val spentAmountText: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val totalBudgetText: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [InsightDataHolder](../-insight-data-holder/index.html) |
| [BudgetMonthlySummaryViewHolder](-budget-monthly-summary-view-holder/index.html) | [androidJvm]<br>class [BudgetMonthlySummaryViewHolder](-budget-monthly-summary-view-holder/index.html)(parent: [ViewGroup](https://developer.android.com/reference/kotlin/android/view/ViewGroup.html), val actionHandler: [ActionHandler](../../com.tink.moneymanagerui.insights.actionhandling/-action-handler/index.html)) : [InsightViewHolder](../-insight-view-holder/index.html), [InsightCommonBottomPart](../-insight-common-bottom-part/index.html) |


## Functions


| Name | Summary |
|---|---|
| [getDataHolder](get-data-holder.html) | [androidJvm]<br>open override fun [getDataHolder](get-data-holder.html)(insight: [Insight](../../com.tink.model.insights/-insight/index.html)): [InsightDataHolder](../-insight-data-holder/index.html) |
| [viewHolder](view-holder.html) | [androidJvm]<br>open override fun [viewHolder](view-holder.html)(parent: [ViewGroup](https://developer.android.com/reference/kotlin/android/view/ViewGroup.html), actionHandler: [ActionHandler](../../com.tink.moneymanagerui.insights.actionhandling/-action-handler/index.html)): [InsightViewHolder](../-insight-view-holder/index.html) |


## Properties


| Name | Summary |
|---|---|
| [supportedInsightTypes](supported-insight-types.html) | [androidJvm]<br>open override val [supportedInsightTypes](supported-insight-types.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[InsightType](../../com.tink.model.insights/-insight-type/index.html)&gt; |
| [viewType](view-type.html) | [androidJvm]<br>open override val [viewType](view-type.html): &lt;ERROR CLASS&gt; |

