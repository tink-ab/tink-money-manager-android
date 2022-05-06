---
title: com.tink.moneymanagerui.accounts.list
---
//[moneymanager-ui](../../index.html)/[com.tink.moneymanagerui.accounts.list](index.html)



# Package com.tink.moneymanagerui.accounts.list



## Types


| Name | Summary |
|---|---|
| [AccountGroup](-account-group/index.html) | [androidJvm]<br>data class [AccountGroup](-account-group/index.html)(@[StringRes](https://developer.android.com/reference/kotlin/androidx/annotation/StringRes.html)val description: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val sortOrder: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [AccountListFragment](-account-list-fragment/index.html) | [androidJvm]<br>class [AccountListFragment](-account-list-fragment/index.html) |
| [GroupedAccountListAdapter](-grouped-account-list-adapter/index.html) | [androidJvm]<br>class [GroupedAccountListAdapter](-grouped-account-list-adapter/index.html)(val accountClicked: ([Account](../com.tink.model.account/-account/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) : [ListAdapter](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/ListAdapter.html)&lt;[GroupedAccountsItem](-grouped-accounts-item/index.html), [GroupedAccountListAdapter.GroupedAccountViewHolder](-grouped-account-list-adapter/-grouped-account-view-holder/index.html)&gt; |
| [GroupedAccountsItem](-grouped-accounts-item/index.html) | [androidJvm]<br>data class [GroupedAccountsItem](-grouped-accounts-item/index.html)(val accountGroup: [AccountGroup](-account-group/index.html), val accounts: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[AccountWithImage](../com.tink.moneymanagerui.accounts/-account-with-image/index.html)&gt;) |
| [NotGroupedAccountListAdapter](-not-grouped-account-list-adapter/index.html) | [androidJvm]<br>class [NotGroupedAccountListAdapter](-not-grouped-account-list-adapter/index.html)(@[AttrRes](https://developer.android.com/reference/kotlin/androidx/annotation/AttrRes.html)val itemBackgroundAttributeId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = R.attr.tink_backgroundColor, val accountClicked: ([Account](../com.tink.model.account/-account/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) : [ListAdapter](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/ListAdapter.html)&lt;[AccountWithImage](../com.tink.moneymanagerui.accounts/-account-with-image/index.html), [NotGroupedAccountListAdapter.AccountViewHolder](-not-grouped-account-list-adapter/-account-view-holder/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [toAccountGroup](to-account-group.html) | [androidJvm]<br>fun [Account.Type](../com.tink.model.account/-account/-type/index.html).[toAccountGroup](to-account-group.html)(): [AccountGroup](-account-group/index.html) |

