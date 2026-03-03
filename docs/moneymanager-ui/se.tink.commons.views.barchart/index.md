---
title: se.tink.commons.views.barchart
---
//[moneymanager-ui](../../index.html)/[se.tink.commons.views.barchart](index.html)



# Package se.tink.commons.views.barchart



## Types


| Name | Summary |
|---|---|
| [OnBarClickListener](-on-bar-click-listener/index.html) | [androidJvm]<br>interface [OnBarClickListener](-on-bar-click-listener/index.html) |
| [TinkBarChartHelper](-tink-bar-chart-helper/index.html) | [androidJvm]<br>object [TinkBarChartHelper](-tink-bar-chart-helper/index.html) |
| [TinkBarChartView](-tink-bar-chart-view/index.html) | [androidJvm]<br>class [TinkBarChartView](-tink-bar-chart-view/index.html)@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)constructor(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), attrs: [AttributeSet](https://developer.android.com/reference/kotlin/android/util/AttributeSet.html)? = null, defStyleAttr: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0) : [TinkBarChartViewBase](-tink-bar-chart-view-base/index.html)<br>To draw bar charts on Canvas |
| [TinkBarChartViewBase](-tink-bar-chart-view-base/index.html) | [androidJvm]<br>open class [TinkBarChartViewBase](-tink-bar-chart-view-base/index.html)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), attrs: [AttributeSet](https://developer.android.com/reference/kotlin/android/util/AttributeSet.html)? = null, defStyleAttr: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0) : [View](https://developer.android.com/reference/kotlin/android/view/View.html) |
| [TinkBarItem](-tink-bar-item/index.html) | [androidJvm]<br>data class [TinkBarItem](-tink-bar-item/index.html)(val nodeValue: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, val nodePosition: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = -1, var startX: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = 0.0f, var startY: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = 0.0f, var endY: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = 0.0f) |


## Functions


| Name | Summary |
|---|---|
| [dpToPx](dp-to-px.html) | [androidJvm]<br>fun [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html).[dpToPx](dp-to-px.html)(): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |


## Properties


| Name | Summary |
|---|---|
| [DEFAULT_FOOTER_BOTTOM_MARGIN](-d-e-f-a-u-l-t_-f-o-o-t-e-r_-b-o-t-t-o-m_-m-a-r-g-i-n.html) | [androidJvm]<br>const val [DEFAULT_FOOTER_BOTTOM_MARGIN](-d-e-f-a-u-l-t_-f-o-o-t-e-r_-b-o-t-t-o-m_-m-a-r-g-i-n.html): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = 20.0f |
| [DEFAULT_HEIGHT_MARGIN](-d-e-f-a-u-l-t_-h-e-i-g-h-t_-m-a-r-g-i-n.html) | [androidJvm]<br>const val [DEFAULT_HEIGHT_MARGIN](-d-e-f-a-u-l-t_-h-e-i-g-h-t_-m-a-r-g-i-n.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 40 |
| [DEFAULT_HORIZONTAL_GAP](-d-e-f-a-u-l-t_-h-o-r-i-z-o-n-t-a-l_-g-a-p.html) | [androidJvm]<br>const val [DEFAULT_HORIZONTAL_GAP](-d-e-f-a-u-l-t_-h-o-r-i-z-o-n-t-a-l_-g-a-p.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 50 |
| [DEFAULT_NEG_ALPHA](-d-e-f-a-u-l-t_-n-e-g_-a-l-p-h-a.html) | [androidJvm]<br>const val [DEFAULT_NEG_ALPHA](-d-e-f-a-u-l-t_-n-e-g_-a-l-p-h-a.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 55 |
| [DEFAULT_NEG_NODE_MARGIN](-d-e-f-a-u-l-t_-n-e-g_-n-o-d-e_-m-a-r-g-i-n.html) | [androidJvm]<br>const val [DEFAULT_NEG_NODE_MARGIN](-d-e-f-a-u-l-t_-n-e-g_-n-o-d-e_-m-a-r-g-i-n.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 40 |
| [DEFAULT_POS_ALPHA](-d-e-f-a-u-l-t_-p-o-s_-a-l-p-h-a.html) | [androidJvm]<br>const val [DEFAULT_POS_ALPHA](-d-e-f-a-u-l-t_-p-o-s_-a-l-p-h-a.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 255 |
| [DEFAULT_POS_NODE_MARGIN](-d-e-f-a-u-l-t_-p-o-s_-n-o-d-e_-m-a-r-g-i-n.html) | [androidJvm]<br>const val [DEFAULT_POS_NODE_MARGIN](-d-e-f-a-u-l-t_-p-o-s_-n-o-d-e_-m-a-r-g-i-n.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 20 |
| [DEFAULT_SECTION_BOTTOM_MARGIN](-d-e-f-a-u-l-t_-s-e-c-t-i-o-n_-b-o-t-t-o-m_-m-a-r-g-i-n.html) | [androidJvm]<br>const val [DEFAULT_SECTION_BOTTOM_MARGIN](-d-e-f-a-u-l-t_-s-e-c-t-i-o-n_-b-o-t-t-o-m_-m-a-r-g-i-n.html): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |
| [DEFAULT_TEXT_COLOR](-d-e-f-a-u-l-t_-t-e-x-t_-c-o-l-o-r.html) | [androidJvm]<br>const val [DEFAULT_TEXT_COLOR](-d-e-f-a-u-l-t_-t-e-x-t_-c-o-l-o-r.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [DEFAULT_TEXT_SIZE](-d-e-f-a-u-l-t_-t-e-x-t_-s-i-z-e.html) | [androidJvm]<br>const val [DEFAULT_TEXT_SIZE](-d-e-f-a-u-l-t_-t-e-x-t_-s-i-z-e.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 18 |
| [MINIMUM_SECTION_HEIGHT](-m-i-n-i-m-u-m_-s-e-c-t-i-o-n_-h-e-i-g-h-t.html) | [androidJvm]<br>const val [MINIMUM_SECTION_HEIGHT](-m-i-n-i-m-u-m_-s-e-c-t-i-o-n_-h-e-i-g-h-t.html): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = 200.0f |

