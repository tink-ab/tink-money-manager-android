---
title: AbstractInsightsViewModel
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.insights](../index.html)/[AbstractInsightsViewModel](index.html)



# AbstractInsightsViewModel



[androidJvm]\
abstract class [AbstractInsightsViewModel](index.html) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)



## Functions


| Name | Summary |
|---|---|
| [clear](../-archived-insights-view-model/index.html#-1936886459%2FFunctions%2F1000845458) | [androidJvm]<br>@[MainThread](https://developer.android.com/reference/kotlin/androidx/annotation/MainThread.html)<br>fun [clear](../-archived-insights-view-model/index.html#-1936886459%2FFunctions%2F1000845458)() |
| [getTag](../-archived-insights-view-model/index.html#-215894976%2FFunctions%2F1000845458) | [androidJvm]<br>open fun &lt;[T](../-archived-insights-view-model/index.html#-215894976%2FFunctions%2F1000845458) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [getTag](../-archived-insights-view-model/index.html#-215894976%2FFunctions%2F1000845458)(p0: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [T](../-archived-insights-view-model/index.html#-215894976%2FFunctions%2F1000845458) |
| [onCleared](../-archived-insights-view-model/index.html#-1930136507%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [onCleared](../-archived-insights-view-model/index.html#-1930136507%2FFunctions%2F1000845458)() |
| [setTagIfAbsent](../-archived-insights-view-model/index.html#-1567230750%2FFunctions%2F1000845458) | [androidJvm]<br>open fun &lt;[T](../-archived-insights-view-model/index.html#-1567230750%2FFunctions%2F1000845458) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [setTagIfAbsent](../-archived-insights-view-model/index.html#-1567230750%2FFunctions%2F1000845458)(p0: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), p1: [T](../-archived-insights-view-model/index.html#-1567230750%2FFunctions%2F1000845458)): [T](../-archived-insights-view-model/index.html#-1567230750%2FFunctions%2F1000845458) |


## Properties


| Name | Summary |
|---|---|
| [hasItems](has-items.html) | [androidJvm]<br>val [hasItems](has-items.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [insights](insights.html) | [androidJvm]<br>val [insights](insights.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Insight](../../com.tink.model.insights/-insight/index.html)&gt;&gt; |
| [loading](loading.html) | [androidJvm]<br>val [loading](loading.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [postProcessor](post-processor.html) | [androidJvm]<br>val [postProcessor](post-processor.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Insight](../../com.tink.model.insights/-insight/index.html)&gt;.() -&gt; [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Insight](../../com.tink.model.insights/-insight/index.html)&gt; |
| [showEmptyState](show-empty-state.html) | [androidJvm]<br>val [showEmptyState](show-empty-state.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |


## Inheritors


| Name |
|---|
| [CurrentInsightsViewModel](../-current-insights-view-model/index.html) |
| [ArchivedInsightsViewModel](../-archived-insights-view-model/index.html) |

