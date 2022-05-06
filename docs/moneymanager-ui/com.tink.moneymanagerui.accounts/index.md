---
title: com.tink.moneymanagerui.accounts
---
//[moneymanager-ui](../../index.html)/[com.tink.moneymanagerui.accounts](index.html)



# Package com.tink.moneymanagerui.accounts



## Types


| Name | Summary |
|---|---|
| [AccountEditConfiguration](-account-edit-configuration/index.html) | [androidJvm]<br>data class [AccountEditConfiguration](-account-edit-configuration/index.html)(val fields: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[EditAccountField](-edit-account-field/index.html)&gt;) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |
| [AccountFilter](-account-filter/index.html) | [androidJvm]<br>fun interface [AccountFilter](-account-filter/index.html)<br>Interface definition to filter accounts |
| [AccountGroupable](-account-groupable/index.html) | [androidJvm]<br>interface [AccountGroupable](-account-groupable/index.html) |
| [AccountGroupByKind](-account-group-by-kind/index.html) | [androidJvm]<br>object [AccountGroupByKind](-account-group-by-kind/index.html) : [AccountGroupType](-account-group-type/index.html), [AccountGroupable](-account-groupable/index.html) |
| [AccountGroupType](-account-group-type/index.html) | [androidJvm]<br>sealed class [AccountGroupType](-account-group-type/index.html) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |
| [AccountWithImage](-account-with-image/index.html) | [androidJvm]<br>data class [AccountWithImage](-account-with-image/index.html)(val account: [Account](../com.tink.model.account/-account/index.html), val iconUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null) |
| [CustomAccountGroup](-custom-account-group/index.html) | [androidJvm]<br>class [CustomAccountGroup](-custom-account-group/index.html)(val grouping: ([List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[AccountWithImage](-account-with-image/index.html)&gt;) -&gt; [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[GroupedAccountsItem](../com.tink.moneymanagerui.accounts.list/-grouped-accounts-item/index.html)&gt;) : [AccountGroupType](-account-group-type/index.html), [AccountGroupable](-account-groupable/index.html) |
| [EditAccountField](-edit-account-field/index.html) | [androidJvm]<br>enum [EditAccountField](-edit-account-field/index.html) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[EditAccountField](-edit-account-field/index.html)&gt; |
| [NoAccountGroup](-no-account-group/index.html) | [androidJvm]<br>object [NoAccountGroup](-no-account-group/index.html) : [AccountGroupType](-account-group-type/index.html) |
| [OverviewAccountsMode](-overview-accounts-mode/index.html) | [androidJvm]<br>sealed class [OverviewAccountsMode](-overview-accounts-mode/index.html) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |
| [OverviewAccountsTypeAccounts](-overview-accounts-type-accounts/index.html) | [androidJvm]<br>class [OverviewAccountsTypeAccounts](-overview-accounts-type-accounts/index.html)(accountType: [Account.Type](../com.tink.model.account/-account/-type/index.html)) : [OverviewAccountsMode](-overview-accounts-mode/index.html) |
| [OverviewAllAccounts](-overview-all-accounts/index.html) | [androidJvm]<br>object [OverviewAllAccounts](-overview-all-accounts/index.html) : [OverviewAccountsMode](-overview-accounts-mode/index.html) |
| [OverviewCredentialsIdAccounts](-overview-credentials-id-accounts/index.html) | [androidJvm]<br>class [OverviewCredentialsIdAccounts](-overview-credentials-id-accounts/index.html)(credentialsId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [OverviewAccountsMode](-overview-accounts-mode/index.html) |
| [OverviewCustomAccounts](-overview-custom-accounts/index.html) | [androidJvm]<br>class [OverviewCustomAccounts](-overview-custom-accounts/index.html)(customAccountFilter: [AccountFilter](-account-filter/index.html)) : [OverviewAccountsMode](-overview-accounts-mode/index.html) |
| [OverviewFavoriteAccounts](-overview-favorite-accounts/index.html) | [androidJvm]<br>object [OverviewFavoriteAccounts](-overview-favorite-accounts/index.html) : [OverviewAccountsMode](-overview-accounts-mode/index.html) |
| [OverviewFinancialInstitutionAccounts](-overview-financial-institution-accounts/index.html) | [androidJvm]<br>class [OverviewFinancialInstitutionAccounts](-overview-financial-institution-accounts/index.html)(financialInstitutionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [OverviewAccountsMode](-overview-accounts-mode/index.html) |

