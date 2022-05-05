---
title: com.tink.moneymanagerui.overview.charts
---
//[moneymanager-ui](../../index.html)/[com.tink.moneymanagerui.overview.charts](index.html)



# Package com.tink.moneymanagerui.overview.charts



## Types


| Name | Summary |
|---|---|
| [BarChartItem](-bar-chart-item/index.html) | [androidJvm]<br>data class [BarChartItem](-bar-chart-item/index.html)(val amountLabel: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val periodLabel: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, @[FloatRange](https://developer.android.com/reference/kotlin/androidx/annotation/FloatRange.html)(from = 0.0, to = 1.0)val barHeightFactor: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html), @[AttrRes](https://developer.android.com/reference/kotlin/androidx/annotation/AttrRes.html)val barColor: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [BarChartItemAdapter](-bar-chart-item-adapter/index.html) | [androidJvm]<br>class [BarChartItemAdapter](-bar-chart-item-adapter/index.html) : [RecyclerView.Adapter](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.Adapter.html)&lt;[BarChartItemViewHolder](-bar-chart-item-view-holder/index.html)&gt; |
| [BarChartItems](-bar-chart-items/index.html) | [androidJvm]<br>class [BarChartItems](-bar-chart-items/index.html)(val list: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[BarChartItem](-bar-chart-item/index.html)&gt;, val averageFactor: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)) : [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[BarChartItem](-bar-chart-item/index.html)&gt; |
| [BarChartItemViewHolder](-bar-chart-item-view-holder/index.html) | [androidJvm]<br>class [BarChartItemViewHolder](-bar-chart-item-view-holder/index.html)(parent: [ViewGroup](https://developer.android.com/reference/kotlin/android/view/ViewGroup.html)) : [RecyclerView.ViewHolder](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.ViewHolder.html) |
| [PeriodSelection](-period-selection/index.html) | [androidJvm]<br>sealed class [PeriodSelection](-period-selection/index.html) |
| [PeriodSelectionDialog](-period-selection-dialog/index.html) | [androidJvm]<br>class [PeriodSelectionDialog](-period-selection-dialog/index.html) : BottomSheetDialogFragment |
| [PieChartDetailsViewModelTest](-pie-chart-details-view-model-test/index.html) | [androidJvm]<br>@ExperimentalCoroutinesApi<br>class [PieChartDetailsViewModelTest](-pie-chart-details-view-model-test/index.html) |
| [StatisticsOverTimeFragment](-statistics-over-time-fragment/index.html) | [androidJvm]<br>class [StatisticsOverTimeFragment](-statistics-over-time-fragment/index.html) |

