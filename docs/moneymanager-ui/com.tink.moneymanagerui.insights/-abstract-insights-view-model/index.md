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
| [addCloseable](../-archived-insights-view-model/index.html#264516373%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [addCloseable](../-archived-insights-view-model/index.html#264516373%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |


## Properties


| Name | Summary |
|---|---|
| [hasItems](has-items.html) | [androidJvm]<br>val [hasItems](has-items.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [insights](insights.html) | [androidJvm]<br>val [insights](insights.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Insight&gt;&gt; |
| [loading](loading.html) | [androidJvm]<br>val [loading](loading.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [postProcessor](post-processor.html) | [androidJvm]<br>val [postProcessor](post-processor.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Insight&gt;.() -&gt; [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Insight&gt; |
| [showEmptyState](show-empty-state.html) | [androidJvm]<br>val [showEmptyState](show-empty-state.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |


## Inheritors


| Name |
|---|
| [CurrentInsightsViewModel](../-current-insights-view-model/index.html) |
| [ArchivedInsightsViewModel](../-archived-insights-view-model/index.html) |

