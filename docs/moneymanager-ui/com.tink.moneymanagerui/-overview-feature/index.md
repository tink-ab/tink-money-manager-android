---
title: OverviewFeature
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui](../index.html)/[OverviewFeature](index.html)



# OverviewFeature



[androidJvm]\
sealed class [OverviewFeature](index.html) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)

This class represents the features that are displayed as individual sections in the overview screen in the financial overview UI. Each section acts as an entry point that end-users can interact with and navigate towards more detailed feature flows.



## Types


| Name | Summary |
|---|---|
| [Accounts](-accounts/index.html) | [androidJvm]<br>class [Accounts](-accounts/index.html)(val overviewAccountsMode: [OverviewAccountsMode](../../com.tink.moneymanagerui.accounts/-overview-accounts-mode/index.html) = OverviewFavoriteAccounts, val accountGroupType: [AccountGroupType](../../com.tink.moneymanagerui.accounts/-account-group-type/index.html) = NoAccountGroup, val accountEditConfiguration: [AccountEditConfiguration](../../com.tink.moneymanagerui.accounts/-account-edit-configuration/index.html) = ALL) : [OverviewFeature](index.html)<br>Represents the accounts list section in the overview screen. |
| [ActionableInsights](-actionable-insights/index.html) | [androidJvm]<br>object [ActionableInsights](-actionable-insights/index.html) : [OverviewFeature](index.html)<br>Represents the actionable insights section in the overview screen. |
| [Budgets](-budgets/index.html) | [androidJvm]<br>object [Budgets](-budgets/index.html) : [OverviewFeature](index.html)<br>Represents the budgets section in the overview screen. |
| [CustomContainerView](-custom-container-view/index.html) | [androidJvm]<br>class [CustomContainerView](-custom-container-view/index.html)(val containerViewId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val width: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val height: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) : [OverviewFeature](index.html)<br>Represents a custom view container that can be added to the overview screen. |
| [LatestTransactions](-latest-transactions/index.html) | [androidJvm]<br>object [LatestTransactions](-latest-transactions/index.html) : [OverviewFeature](index.html)<br>Represents the latest transactions section in the overview screen. This section includes a &quot;See All&quot; action text that the user can click to see a list of all transactions. |
| [Statistics](-statistics/index.html) | [androidJvm]<br>class [Statistics](-statistics/index.html)(val statisticTypes: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[StatisticType](../-statistic-type/index.html)&gt;) : [OverviewFeature](index.html)<br>Represents the statistics charts section in the overview screen. |


## Functions


| Name | Summary |
|---|---|
| [describeContents](../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [describeContents](../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [writeToParcel](../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458)(p0: [Parcel](https://developer.android.com/reference/kotlin/android/os/Parcel.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |


## Inheritors


| Name |
|---|
| [Statistics](-statistics/index.html) |
| [LatestTransactions](-latest-transactions/index.html) |
| [Accounts](-accounts/index.html) |
| [ActionableInsights](-actionable-insights/index.html) |
| [Budgets](-budgets/index.html) |
| [CustomContainerView](-custom-container-view/index.html) |

