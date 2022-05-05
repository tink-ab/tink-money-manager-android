---
title: WeeklyExpensesByDayViewProvider
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.insights.viewproviders](../index.html)/[WeeklyExpensesByDayViewProvider](index.html)



# WeeklyExpensesByDayViewProvider



[androidJvm]\
@ContributesInsightViewProvider



class [WeeklyExpensesByDayViewProvider](index.html)@Injectconstructor(dateUtils: [DateUtils](../../se.tink.utils/-date-utils/index.html)) : [InsightViewProvider](../-insight-view-provider/index.html)



## Constructors


| | |
|---|---|
| [WeeklyExpensesByDayViewProvider](-weekly-expenses-by-day-view-provider.html) | [androidJvm]<br>@Inject<br>fun [WeeklyExpensesByDayViewProvider](-weekly-expenses-by-day-view-provider.html)(dateUtils: [DateUtils](../../se.tink.utils/-date-utils/index.html)) |


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

