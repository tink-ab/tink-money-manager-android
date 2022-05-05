---
title: InsightsDiffUtilCallback
---
//[moneymanager-ui](../../../../index.html)/[com.tink.moneymanagerui.insights](../../index.html)/[InsightsAdapter](../index.html)/[InsightsDiffUtilCallback](index.html)



# InsightsDiffUtilCallback



[androidJvm]\
inner class [InsightsDiffUtilCallback](index.html)(newItems: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Insight](../../../com.tink.model.insights/-insight/index.html)&gt;) : [DiffUtil.Callback](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/DiffUtil.Callback.html)



## Constructors


| | |
|---|---|
| [InsightsDiffUtilCallback](-insights-diff-util-callback.html) | [androidJvm]<br>fun [InsightsDiffUtilCallback](-insights-diff-util-callback.html)(newItems: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Insight](../../../com.tink.model.insights/-insight/index.html)&gt;) |


## Functions


| Name | Summary |
|---|---|
| [areContentsTheSame](are-contents-the-same.html) | [androidJvm]<br>open override fun [areContentsTheSame](are-contents-the-same.html)(oldItemPosition: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), newItemPosition: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [areItemsTheSame](are-items-the-same.html) | [androidJvm]<br>open override fun [areItemsTheSame](are-items-the-same.html)(oldItemPosition: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), newItemPosition: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [getChangePayload](index.html#-1911713805%2FFunctions%2F1000845458) | [androidJvm]<br>@[Nullable](https://developer.android.com/reference/kotlin/androidx/annotation/Nullable.html)<br>open fun [getChangePayload](index.html#-1911713805%2FFunctions%2F1000845458)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)? |
| [getNewListSize](get-new-list-size.html) | [androidJvm]<br>open override fun [getNewListSize](get-new-list-size.html)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getOldListSize](get-old-list-size.html) | [androidJvm]<br>open override fun [getOldListSize](get-old-list-size.html)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

