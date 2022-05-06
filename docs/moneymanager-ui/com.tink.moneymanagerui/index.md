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
| [MoneyManagerFeatureType](-money-manager-feature-type/index.html) | [androidJvm]<br>enum [MoneyManagerFeatureType](-money-manager-feature-type/index.html) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[MoneyManagerFeatureType](-money-manager-feature-type/index.html)&gt; |
| [OverviewFeature](-overview-feature/index.html) | [androidJvm]<br>sealed class [OverviewFeature](-overview-feature/index.html) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)<br>This class represents the features that are displayed as individual sections in the overview screen in the financial overview UI. Each section acts as an entry point that end-users can interact with and navigate towards more detailed feature flows. |
| [OverviewFeatures](-overview-features/index.html) | [androidJvm]<br>data class [OverviewFeatures](-overview-features/index.html)(val features: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[OverviewFeature](-overview-feature/index.html)&gt;) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)<br>A wrapper class containing a list of the [OverviewFeature](-overview-feature/index.html) objects that users can pass as input while creating an instance of the [FinanceOverviewFragment](-finance-overview-fragment/index.html). |
| [StatisticType](-statistic-type/index.html) | [androidJvm]<br>enum [StatisticType](-statistic-type/index.html) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[StatisticType](-statistic-type/index.html)&gt; <br>Represents the type of statistics data that can be displayed as a chart. |
| [TestConfiguration](-test-configuration/index.html) | [androidJvm]<br>class [TestConfiguration](-test-configuration/index.html)(url: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [TransitionCoordinator](-transition-coordinator/index.html) | [androidJvm]<br>interface [TransitionCoordinator](-transition-coordinator/index.html) |
| [ViewModelFactory](-view-model-factory/index.html) | [androidJvm]<br>open class [ViewModelFactory](-view-model-factory/index.html)@Injectconstructor(providers: ModelProviders) : [ViewModelProvider.Factory](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModelProvider.Factory.html)<br>View model provider factory allows injection into view models |

