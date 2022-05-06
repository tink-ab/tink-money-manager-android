---
title: InsightsViewModel
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.insights](../index.html)/[InsightsViewModel](index.html)



# InsightsViewModel



[androidJvm]\
interface [InsightsViewModel](index.html)



## Functions


| Name | Summary |
|---|---|
| [refresh](refresh.html) | [androidJvm]<br>abstract fun [refresh](refresh.html)() |


## Properties


| Name | Summary |
|---|---|
| [errors](errors.html) | [androidJvm]<br>abstract val [errors](errors.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Event](../../se.tink.commons.livedata/-event/index.html)&lt;[TinkNetworkError](../../se.tink.android.repository/-tink-network-error/index.html)&gt;?&gt; |
| [hasItems](has-items.html) | [androidJvm]<br>abstract val [hasItems](has-items.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [insights](insights.html) | [androidJvm]<br>abstract val [insights](insights.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Insight](../../com.tink.model.insights/-insight/index.html)&gt;&gt; |
| [loading](loading.html) | [androidJvm]<br>abstract val [loading](loading.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [showEmptyState](show-empty-state.html) | [androidJvm]<br>abstract val [showEmptyState](show-empty-state.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |


## Inheritors


| Name |
|---|
| [CurrentInsightsViewModel](../-current-insights-view-model/index.html) |
| [ArchivedInsightsViewModel](../-archived-insights-view-model/index.html) |

