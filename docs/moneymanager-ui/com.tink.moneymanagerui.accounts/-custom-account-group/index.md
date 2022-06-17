---
title: CustomAccountGroup
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.accounts](../index.html)/[CustomAccountGroup](index.html)



# CustomAccountGroup



[androidJvm]\
class [CustomAccountGroup](index.html)(val grouping: ([List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[AccountWithImage](../-account-with-image/index.html)&gt;) -&gt; [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[GroupedAccountsItem](../../com.tink.moneymanagerui.accounts.list/-grouped-accounts-item/index.html)&gt;) : [AccountGroupType](../-account-group-type/index.html), [AccountGroupable](../-account-groupable/index.html)



## Constructors


| | |
|---|---|
| [CustomAccountGroup](-custom-account-group.html) | [androidJvm]<br>fun [CustomAccountGroup](-custom-account-group.html)(grouping: ([List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[AccountWithImage](../-account-with-image/index.html)&gt;) -&gt; [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[GroupedAccountsItem](../../com.tink.moneymanagerui.accounts.list/-grouped-accounts-item/index.html)&gt;) |


## Functions


| Name | Summary |
|---|---|
| [describeContents](../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [describeContents](../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [groupAccounts](group-accounts.html) | [androidJvm]<br>open override fun [groupAccounts](group-accounts.html)(accounts: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[AccountWithImage](../-account-with-image/index.html)&gt;): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[GroupedAccountsItem](../../com.tink.moneymanagerui.accounts.list/-grouped-accounts-item/index.html)&gt; |
| [writeToParcel](../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [writeToParcel](../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458)(p0: [Parcel](https://developer.android.com/reference/kotlin/android/os/Parcel.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [areAccountsGrouped](../-account-group-type/are-accounts-grouped.html) | [androidJvm]<br>val [areAccountsGrouped](../-account-group-type/are-accounts-grouped.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [grouping](grouping.html) | [androidJvm]<br>val [grouping](grouping.html): ([List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[AccountWithImage](../-account-with-image/index.html)&gt;) -&gt; [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[GroupedAccountsItem](../../com.tink.moneymanagerui.accounts.list/-grouped-accounts-item/index.html)&gt; |
