---
title: TransactionsSummaryViewProvider
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.insights.viewproviders](../index.html)/[TransactionsSummaryViewProvider](index.html)



# TransactionsSummaryViewProvider



[androidJvm]\
@ContributesInsightViewProvider



class [TransactionsSummaryViewProvider](index.html)@Injectconstructor(val dateUtils: [DateUtils](../../se.tink.utils/-date-utils/index.html)) : [InsightViewProvider](../-insight-view-provider/index.html)



## Constructors


| | |
|---|---|
| [TransactionsSummaryViewProvider](-transactions-summary-view-provider.html) | [androidJvm]<br>@Inject<br>fun [TransactionsSummaryViewProvider](-transactions-summary-view-provider.html)(dateUtils: [DateUtils](../../se.tink.utils/-date-utils/index.html)) |


## Types


| Name | Summary |
|---|---|
| [TransactionsSummaryViewHolder](-transactions-summary-view-holder/index.html) | [androidJvm]<br>inner class [TransactionsSummaryViewHolder](-transactions-summary-view-holder/index.html)(parent: [ViewGroup](https://developer.android.com/reference/kotlin/android/view/ViewGroup.html), val actionHandler: [ActionHandler](../../com.tink.moneymanagerui.insights.actionhandling/-action-handler/index.html)) : [InsightViewHolder](../-insight-view-holder/index.html), [InsightCommonBottomPart](../-insight-common-bottom-part/index.html) |


## Functions


| Name | Summary |
|---|---|
| [getDataHolder](get-data-holder.html) | [androidJvm]<br>open override fun [getDataHolder](get-data-holder.html)(insight: [Insight](../../com.tink.model.insights/-insight/index.html)): [InsightDataHolder](../-insight-data-holder/index.html) |
| [viewHolder](view-holder.html) | [androidJvm]<br>open override fun [viewHolder](view-holder.html)(parent: [ViewGroup](https://developer.android.com/reference/kotlin/android/view/ViewGroup.html), actionHandler: [ActionHandler](../../com.tink.moneymanagerui.insights.actionhandling/-action-handler/index.html)): [InsightViewHolder](../-insight-view-holder/index.html) |


## Properties


| Name | Summary |
|---|---|
| [dateUtils](date-utils.html) | [androidJvm]<br>val [dateUtils](date-utils.html): [DateUtils](../../se.tink.utils/-date-utils/index.html) |
| [supportedInsightTypes](supported-insight-types.html) | [androidJvm]<br>open override val [supportedInsightTypes](supported-insight-types.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[InsightType](../../com.tink.model.insights/-insight-type/index.html)&gt; |
| [viewType](view-type.html) | [androidJvm]<br>open override val [viewType](view-type.html): &lt;ERROR CLASS&gt; |

