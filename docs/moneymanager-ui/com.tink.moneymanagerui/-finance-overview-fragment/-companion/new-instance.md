---
title: newInstance
---
//[moneymanager-ui](../../../../index.html)/[com.tink.moneymanagerui](../../index.html)/[FinanceOverviewFragment](../index.html)/[Companion](index.html)/[newInstance](new-instance.html)



# newInstance



[androidJvm]\




@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)



@[JvmStatic](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-static/index.html)



fun [newInstance](new-instance.html)(accessToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), @[StyleRes](https://developer.android.com/reference/kotlin/androidx/annotation/StyleRes.html)styleResId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), tracker: [Tracker](../../../com.tink.moneymanagerui.tracking/-tracker/index.html)? = null, overviewFeatures: [OverviewFeatures](../../-overview-features/index.html) = OverviewFeatures.ALL, insightActionHandler: [InsightActionHandler](../../../com.tink.moneymanagerui.insights.actionhandling/-insight-action-handler/index.html)? = null, backPressedListener: [OnBackPressedListener](../../../com.tink.moneymanagerui.configuration/-on-back-pressed-listener/index.html)? = null, isOverviewToolbarVisible: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, javaInsightActionHandler: [JavaInsightActionHandler](../../../com.tink.moneymanagerui.insights.actionhandling/-java-insight-action-handler/index.html)? = null, featureSpecificThemes: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[MoneyManagerFeatureType](../../-money-manager-feature-type/index.html), [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt; = emptyMap()): [FinanceOverviewFragment](../index.html)



Creates a new instance of the [FinanceOverviewFragment](../index.html).



## Parameters


androidJvm

| | |
|---|---|
| accessToken | A valid access token for the user |
| styleResId | The resource ID of your style that extends R.style.TinkFinanceOverviewStyle |
| tracker | An optional [Tracker](../../../com.tink.moneymanagerui.tracking/-tracker/index.html) implementation |
| overviewFeatures | The [OverviewFeatures](../../-overview-features/index.html) object with the list of overview features to be included |
| insightActionHandler | The optional [InsightActionHandler](../../../com.tink.moneymanagerui.insights.actionhandling/-insight-action-handler/index.html) implementation for custom handling of Insight.Action |
| backPressedListener | An optional [OnBackPressedListener](../../../com.tink.moneymanagerui.configuration/-on-back-pressed-listener/index.html) callback to listen to back presses in the SDK |
| isOverviewToolbarVisible | Set if you want to show a toolbar for the overview fragment, defaults to false |
| javaInsightActionHandler | an optional [JavaInsightActionHandler](../../../com.tink.moneymanagerui.insights.actionhandling/-java-insight-action-handler/index.html) you can use to handle insights when not using Kotlin, not used if insightActionHandler is set |
| featureSpecificThemes | an optional Map where you can set specific themes for Money Manager's individual features. The keys in the map should be the [MoneyManagerFeatureType](../../-money-manager-feature-type/index.html) and the values the theme for that specific feature. It's recommended to have your feature specific theme inherit from your app's base theme |




