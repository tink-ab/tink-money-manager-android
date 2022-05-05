---
title: BarChartItemAdapter
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.overview.charts](../index.html)/[BarChartItemAdapter](index.html)



# BarChartItemAdapter



[androidJvm]\
class [BarChartItemAdapter](index.html) : [RecyclerView.Adapter](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.Adapter.html)&lt;[BarChartItemViewHolder](../-bar-chart-item-view-holder/index.html)&gt;



## Constructors


| | |
|---|---|
| [BarChartItemAdapter](-bar-chart-item-adapter.html) | [androidJvm]<br>fun [BarChartItemAdapter](-bar-chart-item-adapter.html)() |


## Functions


| Name | Summary |
|---|---|
| [bindViewHolder](index.html#50202115%2FFunctions%2F1000845458) | [androidJvm]<br>fun [bindViewHolder](index.html#50202115%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [BarChartItemViewHolder](../-bar-chart-item-view-holder/index.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [canRestoreState](index.html#-533870738%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [canRestoreState](index.html#-533870738%2FFunctions%2F1000845458)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [createViewHolder](index.html#1423244545%2FFunctions%2F1000845458) | [androidJvm]<br>@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)<br>fun [createViewHolder](index.html#1423244545%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [ViewGroup](https://developer.android.com/reference/kotlin/android/view/ViewGroup.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [BarChartItemViewHolder](../-bar-chart-item-view-holder/index.html) |
| [findRelativeAdapterPositionIn](index.html#-1238180073%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [findRelativeAdapterPositionIn](index.html#-1238180073%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [RecyclerView.Adapter](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.Adapter.html)&lt;out [RecyclerView.ViewHolder](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.ViewHolder.html)&gt;, @[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p1: [RecyclerView.ViewHolder](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.ViewHolder.html), p2: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getItemCount](get-item-count.html) | [androidJvm]<br>open override fun [getItemCount](get-item-count.html)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getItemId](index.html#725914875%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [getItemId](index.html#725914875%2FFunctions%2F1000845458)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [getItemViewType](index.html#714126295%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [getItemViewType](index.html#714126295%2FFunctions%2F1000845458)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getStateRestorationPolicy](index.html#1717359980%2FFunctions%2F1000845458) | [androidJvm]<br>@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)<br>fun [getStateRestorationPolicy](index.html#1717359980%2FFunctions%2F1000845458)(): [RecyclerView.Adapter.StateRestorationPolicy](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.Adapter.StateRestorationPolicy.html) |
| [hasObservers](index.html#1092162006%2FFunctions%2F1000845458) | [androidJvm]<br>fun [hasObservers](index.html#1092162006%2FFunctions%2F1000845458)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hasStableIds](index.html#16685238%2FFunctions%2F1000845458) | [androidJvm]<br>fun [hasStableIds](index.html#16685238%2FFunctions%2F1000845458)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [notifyDataSetChanged](index.html#-1095556076%2FFunctions%2F1000845458) | [androidJvm]<br>fun [notifyDataSetChanged](index.html#-1095556076%2FFunctions%2F1000845458)() |
| [notifyItemChanged](index.html#-1721030169%2FFunctions%2F1000845458) | [androidJvm]<br>fun [notifyItemChanged](index.html#-1721030169%2FFunctions%2F1000845458)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>fun [notifyItemChanged](index.html#748267402%2FFunctions%2F1000845458)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), @[Nullable](https://developer.android.com/reference/kotlin/androidx/annotation/Nullable.html)p1: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?) |
| [notifyItemInserted](index.html#2137269507%2FFunctions%2F1000845458) | [androidJvm]<br>fun [notifyItemInserted](index.html#2137269507%2FFunctions%2F1000845458)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [notifyItemMoved](index.html#-1694317867%2FFunctions%2F1000845458) | [androidJvm]<br>fun [notifyItemMoved](index.html#-1694317867%2FFunctions%2F1000845458)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [notifyItemRangeChanged](index.html#1769183193%2FFunctions%2F1000845458) | [androidJvm]<br>fun [notifyItemRangeChanged](index.html#1769183193%2FFunctions%2F1000845458)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>fun [notifyItemRangeChanged](index.html#1916975740%2FFunctions%2F1000845458)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), @[Nullable](https://developer.android.com/reference/kotlin/androidx/annotation/Nullable.html)p2: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?) |
| [notifyItemRangeInserted](index.html#-2104748521%2FFunctions%2F1000845458) | [androidJvm]<br>fun [notifyItemRangeInserted](index.html#-2104748521%2FFunctions%2F1000845458)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [notifyItemRangeRemoved](index.html#999899269%2FFunctions%2F1000845458) | [androidJvm]<br>fun [notifyItemRangeRemoved](index.html#999899269%2FFunctions%2F1000845458)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [notifyItemRemoved](index.html#-189254469%2FFunctions%2F1000845458) | [androidJvm]<br>fun [notifyItemRemoved](index.html#-189254469%2FFunctions%2F1000845458)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [onAttachedToRecyclerView](index.html#-1243461790%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [onAttachedToRecyclerView](index.html#-1243461790%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [RecyclerView](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.html)) |
| [onBindViewHolder](on-bind-view-holder.html) | [androidJvm]<br>open override fun [onBindViewHolder](on-bind-view-holder.html)(holder: [BarChartItemViewHolder](../-bar-chart-item-view-holder/index.html), position: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>open fun [onBindViewHolder](index.html#-1267844868%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [BarChartItemViewHolder](../-bar-chart-item-view-holder/index.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), @[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p2: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt;) |
| [onCreateViewHolder](on-create-view-holder.html) | [androidJvm]<br>open override fun [onCreateViewHolder](on-create-view-holder.html)(parent: [ViewGroup](https://developer.android.com/reference/kotlin/android/view/ViewGroup.html), viewType: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [BarChartItemViewHolder](../-bar-chart-item-view-holder/index.html) |
| [onDetachedFromRecyclerView](index.html#-1201433889%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [onDetachedFromRecyclerView](index.html#-1201433889%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [RecyclerView](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.html)) |
| [onFailedToRecycleView](index.html#1867524879%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [onFailedToRecycleView](index.html#1867524879%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [BarChartItemViewHolder](../-bar-chart-item-view-holder/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [onViewAttachedToWindow](index.html#1920354549%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [onViewAttachedToWindow](index.html#1920354549%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [BarChartItemViewHolder](../-bar-chart-item-view-holder/index.html)) |
| [onViewDetachedFromWindow](index.html#5478680%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [onViewDetachedFromWindow](index.html#5478680%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [BarChartItemViewHolder](../-bar-chart-item-view-holder/index.html)) |
| [onViewRecycled](index.html#-1582360941%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [onViewRecycled](index.html#-1582360941%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [BarChartItemViewHolder](../-bar-chart-item-view-holder/index.html)) |
| [registerAdapterDataObserver](index.html#-149943229%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [registerAdapterDataObserver](index.html#-149943229%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [RecyclerView.AdapterDataObserver](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.AdapterDataObserver.html)) |
| [setHasStableIds](index.html#1991189249%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [setHasStableIds](index.html#1991189249%2FFunctions%2F1000845458)(p0: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [setStateRestorationPolicy](index.html#1439711293%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [setStateRestorationPolicy](index.html#1439711293%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [RecyclerView.Adapter.StateRestorationPolicy](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.Adapter.StateRestorationPolicy.html)) |
| [unregisterAdapterDataObserver](index.html#607934410%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [unregisterAdapterDataObserver](index.html#607934410%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [RecyclerView.AdapterDataObserver](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.AdapterDataObserver.html)) |


## Properties


| Name | Summary |
|---|---|
| [averageHeightFactor](average-height-factor.html) | [androidJvm]<br>var [averageHeightFactor](average-height-factor.html): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = 0.0f |
| [items](items.html) | [androidJvm]<br>var [items](items.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[BarChartItem](../-bar-chart-item/index.html)&gt; |

