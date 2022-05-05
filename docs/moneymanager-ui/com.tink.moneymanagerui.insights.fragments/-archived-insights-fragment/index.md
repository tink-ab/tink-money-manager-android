---
title: ArchivedInsightsFragment
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.insights.fragments](../index.html)/[ArchivedInsightsFragment](index.html)



# ArchivedInsightsFragment



[androidJvm]\
class [ArchivedInsightsFragment](index.html)



## Constructors


| | |
|---|---|
| [ArchivedInsightsFragment](-archived-insights-fragment.html) | [androidJvm]<br>fun [ArchivedInsightsFragment](-archived-insights-fragment.html)() |


## Types


| Name | Summary |
|---|---|
| [Companion](-companion/index.html) | [androidJvm]<br>object [Companion](-companion/index.html) |


## Functions


| Name | Summary |
|---|---|
| [authorizedOnCreate](authorized-on-create.html) | [androidJvm]<br>open fun [authorizedOnCreate](authorized-on-create.html)(savedInstanceState: [Bundle](https://developer.android.com/reference/kotlin/android/os/Bundle.html)?) |
| [authorizedOnStart](authorized-on-start.html) | [androidJvm]<br>open fun [authorizedOnStart](authorized-on-start.html)() |
| [authorizedOnViewCreated](authorized-on-view-created.html) | [androidJvm]<br>open fun [authorizedOnViewCreated](authorized-on-view-created.html)(view: [View](https://developer.android.com/reference/kotlin/android/view/View.html), savedInstanceState: [Bundle](https://developer.android.com/reference/kotlin/android/os/Bundle.html)?) |
| [doNotRecreateView](do-not-recreate-view.html) | [androidJvm]<br>open fun [doNotRecreateView](do-not-recreate-view.html)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [getLayoutId](get-layout-id.html) | [androidJvm]<br>open fun [getLayoutId](get-layout-id.html)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getMoneyManagerFeatureType](get-money-manager-feature-type.html) | [androidJvm]<br>open fun [getMoneyManagerFeatureType](get-money-manager-feature-type.html)(): [MoneyManagerFeatureType](../../com.tink.moneymanagerui/-money-manager-feature-type/index.html) |
| [getScreenEvent](get-screen-event.html) | [androidJvm]<br>open fun [getScreenEvent](get-screen-event.html)(): [ScreenEvent](../../com.tink.moneymanagerui.tracking/-screen-event/index.html) |
| [getTitle](get-title.html) | [androidJvm]<br>open fun [getTitle](get-title.html)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [hasToolbar](has-toolbar.html) | [androidJvm]<br>open fun [hasToolbar](has-toolbar.html)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [needsLoginToBeAuthorized](needs-login-to-be-authorized.html) | [androidJvm]<br>open fun [needsLoginToBeAuthorized](needs-login-to-be-authorized.html)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |


## Properties


| Name | Summary |
|---|---|
| [insightsAdapter](insights-adapter.html) | [androidJvm]<br>@Inject<br>lateinit var [insightsAdapter](insights-adapter.html): [InsightsAdapter](../../com.tink.moneymanagerui.insights/-insights-adapter/index.html) |
| [localViewModelFactory](local-view-model-factory.html) | [androidJvm]<br>@Inject<br>lateinit var [localViewModelFactory](local-view-model-factory.html): [InsightsViewModelFactory](../../com.tink.moneymanagerui.insights.di/-insights-view-model-factory/index.html) |
| [viewModel](view-model.html) | [androidJvm]<br>lateinit var [viewModel](view-model.html): [ArchivedInsightsViewModel](../../com.tink.moneymanagerui.insights/-archived-insights-view-model/index.html) |

