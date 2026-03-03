---
title: com.tink.moneymanagerui
---
//[moneymanager-ui](../../index.html)/[com.tink.moneymanagerui](index.html)



# Package com.tink.moneymanagerui



## Types


| Name | Summary |
|---|---|
| [BaseFragment](-base-fragment/index.html) | [androidJvm]<br>abstract class [BaseFragment](-base-fragment/index.html) : [Fragment](https://developer.android.com/reference/kotlin/androidx/fragment/app/Fragment.html), HasAndroidInjector |
| [BaseTestSuite](-base-test-suite/index.html) | [androidJvm]<br>abstract class [BaseTestSuite](-base-test-suite/index.html) |
| [FinanceOverviewFragment](-finance-overview-fragment/index.html) | [androidJvm]<br>class [FinanceOverviewFragment](-finance-overview-fragment/index.html) : [Fragment](https://developer.android.com/reference/kotlin/androidx/fragment/app/Fragment.html), HasAndroidInjector<br>Fragment responsible for displaying an overview of the end-user's finances. This is the entry point to the various flows which are part of the finance overview UI. |
| [FragmentAnimationFlags](-fragment-animation-flags/index.html) | [androidJvm]<br>enum [FragmentAnimationFlags](-fragment-animation-flags/index.html) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[FragmentAnimationFlags](-fragment-animation-flags/index.html)&gt; |
| [FragmentCoordinator](-fragment-coordinator/index.html) | [androidJvm]<br>@[UiThread](https://developer.android.com/reference/kotlin/androidx/annotation/UiThread.html)<br>class [FragmentCoordinator](-fragment-coordinator/index.html)(fragmentManager: [FragmentManager](https://developer.android.com/reference/kotlin/androidx/fragment/app/FragmentManager.html), containerViewId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), transitionCoordinator: [TransitionCoordinatorImpl](-transition-coordinator-impl/index.html)? = null)<br>All the methods should be called when UI change is allowed |
| [MoneyManagerFeatureType](-money-manager-feature-type/index.html) | [androidJvm]<br>enum [MoneyManagerFeatureType](-money-manager-feature-type/index.html) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[MoneyManagerFeatureType](-money-manager-feature-type/index.html)&gt; |
| [OverviewFeature](-overview-feature/index.html) | [androidJvm]<br>sealed class [OverviewFeature](-overview-feature/index.html) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)<br>This class represents the features that are displayed as individual sections in the overview screen in the financial overview UI. Each section acts as an entry point that end-users can interact with and navigate towards more detailed feature flows. |
| [OverviewFeatures](-overview-features/index.html) | [androidJvm]<br>data class [OverviewFeatures](-overview-features/index.html)(val features: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[OverviewFeature](-overview-feature/index.html)&gt;) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)<br>A wrapper class containing a list of the [OverviewFeature](-overview-feature/index.html) objects that users can pass as input while creating an instance of the [FinanceOverviewFragment](-finance-overview-fragment/index.html). |
| [StatisticType](-statistic-type/index.html) | [androidJvm]<br>enum [StatisticType](-statistic-type/index.html) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[StatisticType](-statistic-type/index.html)&gt; <br>Represents the type of statistics data that can be displayed as a chart. |
| [TestConfiguration](-test-configuration/index.html) | [androidJvm]<br>class [TestConfiguration](-test-configuration/index.html)(url: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [TransitionCoordinator](-transition-coordinator/index.html) | [androidJvm]<br>interface [TransitionCoordinator](-transition-coordinator/index.html) |
| [TransitionCoordinatorImpl](-transition-coordinator-impl/index.html) | [androidJvm]<br>@[UiThread](https://developer.android.com/reference/kotlin/androidx/annotation/UiThread.html)<br>class [TransitionCoordinatorImpl](-transition-coordinator-impl/index.html)(transitions: [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[TransitionDescription](-transition-description/index.html)&gt;) : [TransitionCoordinator](-transition-coordinator/index.html) |
| [TransitionDescription](-transition-description/index.html) | [androidJvm]<br>interface [TransitionDescription](-transition-description/index.html) |
| [ViewModelFactory](-view-model-factory/index.html) | [androidJvm]<br>open class [ViewModelFactory](-view-model-factory/index.html)@Injectconstructor(providers: ModelProviders) : [ViewModelProvider.Factory](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModelProvider.Factory.html)<br>View model provider factory allows injection into view models |


## Properties


| Name | Summary |
|---|---|
| [ARG_ACCESS_TOKEN](-a-r-g_-a-c-c-e-s-s_-t-o-k-e-n.html) | [androidJvm]<br>const val [ARG_ACCESS_TOKEN](-a-r-g_-a-c-c-e-s-s_-t-o-k-e-n.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [ARG_BACK_LTR](-a-r-g_-b-a-c-k_-l-t-r.html) | [androidJvm]<br>const val [ARG_BACK_LTR](-a-r-g_-b-a-c-k_-l-t-r.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [ARG_FEATURE_SPECIFIC_THEME](-a-r-g_-f-e-a-t-u-r-e_-s-p-e-c-i-f-i-c_-t-h-e-m-e.html) | [androidJvm]<br>const val [ARG_FEATURE_SPECIFIC_THEME](-a-r-g_-f-e-a-t-u-r-e_-s-p-e-c-i-f-i-c_-t-h-e-m-e.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [ARG_FIRST_LAUNCH](-a-r-g_-f-i-r-s-t_-l-a-u-n-c-h.html) | [androidJvm]<br>const val [ARG_FIRST_LAUNCH](-a-r-g_-f-i-r-s-t_-l-a-u-n-c-h.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [ARG_IS_OVERVIEW_TOOLBAR_VISIBLE](-a-r-g_-i-s_-o-v-e-r-v-i-e-w_-t-o-o-l-b-a-r_-v-i-s-i-b-l-e.html) | [androidJvm]<br>const val [ARG_IS_OVERVIEW_TOOLBAR_VISIBLE](-a-r-g_-i-s_-o-v-e-r-v-i-e-w_-t-o-o-l-b-a-r_-v-i-s-i-b-l-e.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [ARG_OVERVIEW_FEATURES](-a-r-g_-o-v-e-r-v-i-e-w_-f-e-a-t-u-r-e-s.html) | [androidJvm]<br>const val [ARG_OVERVIEW_FEATURES](-a-r-g_-o-v-e-r-v-i-e-w_-f-e-a-t-u-r-e-s.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [ARG_STYLE_RES](-a-r-g_-s-t-y-l-e_-r-e-s.html) | [androidJvm]<br>const val [ARG_STYLE_RES](-a-r-g_-s-t-y-l-e_-r-e-s.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [ARG_STYLE_RES_ID](-a-r-g_-s-t-y-l-e_-r-e-s_-i-d.html) | [androidJvm]<br>const val [ARG_STYLE_RES_ID](-a-r-g_-s-t-y-l-e_-r-e-s_-i-d.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [ARG_TRACKER](-a-r-g_-t-r-a-c-k-e-r.html) | [androidJvm]<br>const val [ARG_TRACKER](-a-r-g_-t-r-a-c-k-e-r.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

