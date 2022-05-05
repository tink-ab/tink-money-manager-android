---
title: CurrentInsightsViewModel
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.insights](../index.html)/[CurrentInsightsViewModel](index.html)



# CurrentInsightsViewModel



[androidJvm]\
class [CurrentInsightsViewModel](index.html) : [AbstractInsightsViewModel](../-abstract-insights-view-model/index.html), [InsightsViewModel](../-insights-view-model/index.html)



## Functions


| Name | Summary |
|---|---|
| [clear](../-archived-insights-view-model/index.html#-1936886459%2FFunctions%2F1000845458) | [androidJvm]<br>@[MainThread](https://developer.android.com/reference/kotlin/androidx/annotation/MainThread.html)<br>fun [clear](../-archived-insights-view-model/index.html#-1936886459%2FFunctions%2F1000845458)() |
| [getTag](../-archived-insights-view-model/index.html#-215894976%2FFunctions%2F1000845458) | [androidJvm]<br>open fun &lt;[T](../-archived-insights-view-model/index.html#-215894976%2FFunctions%2F1000845458) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [getTag](../-archived-insights-view-model/index.html#-215894976%2FFunctions%2F1000845458)(p0: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [T](../-archived-insights-view-model/index.html#-215894976%2FFunctions%2F1000845458) |
| [onCleared](../-archived-insights-view-model/index.html#-1930136507%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [onCleared](../-archived-insights-view-model/index.html#-1930136507%2FFunctions%2F1000845458)() |
| [refresh](refresh.html) | [androidJvm]<br>open override fun [refresh](refresh.html)() |
| [setTagIfAbsent](../-archived-insights-view-model/index.html#-1567230750%2FFunctions%2F1000845458) | [androidJvm]<br>open fun &lt;[T](../-archived-insights-view-model/index.html#-1567230750%2FFunctions%2F1000845458) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [setTagIfAbsent](../-archived-insights-view-model/index.html#-1567230750%2FFunctions%2F1000845458)(p0: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), p1: [T](../-archived-insights-view-model/index.html#-1567230750%2FFunctions%2F1000845458)): [T](../-archived-insights-view-model/index.html#-1567230750%2FFunctions%2F1000845458) |


## Properties


| Name | Summary |
|---|---|
| [errors](errors.html) | [androidJvm]<br>open override val [errors](errors.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Event](../../se.tink.commons.livedata/-event/index.html)&lt;[TinkNetworkError](../../se.tink.android.repository/-tink-network-error/index.html)&gt;?&gt; |
| [hasItems](../-abstract-insights-view-model/has-items.html) | [androidJvm]<br>val [hasItems](../-abstract-insights-view-model/has-items.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [insights](../-abstract-insights-view-model/insights.html) | [androidJvm]<br>val [insights](../-abstract-insights-view-model/insights.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Insight](../../com.tink.model.insights/-insight/index.html)&gt;&gt; |
| [loading](../-abstract-insights-view-model/loading.html) | [androidJvm]<br>val [loading](../-abstract-insights-view-model/loading.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [postProcessor](../-abstract-insights-view-model/post-processor.html) | [androidJvm]<br>val [postProcessor](../-abstract-insights-view-model/post-processor.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Insight](../../com.tink.model.insights/-insight/index.html)&gt;.() -&gt; [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Insight](../../com.tink.model.insights/-insight/index.html)&gt; |
| [showEmptyState](../-abstract-insights-view-model/show-empty-state.html) | [androidJvm]<br>val [showEmptyState](../-abstract-insights-view-model/show-empty-state.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |

