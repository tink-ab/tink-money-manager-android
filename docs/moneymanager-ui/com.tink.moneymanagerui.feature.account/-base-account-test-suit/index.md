---
title: BaseAccountTestSuit
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.feature.account](../index.html)/[BaseAccountTestSuit](index.html)



# BaseAccountTestSuit



[androidJvm]\
open class [BaseAccountTestSuit](index.html) : [BaseTestSuite](../../com.tink.moneymanagerui/-base-test-suite/index.html)



## Constructors


| | |
|---|---|
| [BaseAccountTestSuit](-base-account-test-suit.html) | [androidJvm]<br>fun [BaseAccountTestSuit](-base-account-test-suit.html)() |


## Functions


| Name | Summary |
|---|---|
| [openAccountDetailsFromOverview](open-account-details-from-overview.html) | [androidJvm]<br>fun [openAccountDetailsFromOverview](open-account-details-from-overview.html)(overviewAccountsMode: [OverviewAccountsMode](../../com.tink.moneymanagerui.accounts/-overview-accounts-mode/index.html) = OverviewFavoriteAccounts, accountGroupType: [AccountGroupType](../../com.tink.moneymanagerui.accounts/-account-group-type/index.html) = NoAccountGroup, accountEditConfiguration: [AccountEditConfiguration](../../com.tink.moneymanagerui.accounts/-account-edit-configuration/index.html) = AccountEditConfiguration.ALL, isEditableOnPendingTrx: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) |
| [openTransactionDetailsFromAccountDetails](open-transaction-details-from-account-details.html) | [androidJvm]<br>fun [openTransactionDetailsFromAccountDetails](open-transaction-details-from-account-details.html)() |
| [prepareMockServerForAccountListResponse](prepare-mock-server-for-account-list-response.html) | [androidJvm]<br>fun [prepareMockServerForAccountListResponse](prepare-mock-server-for-account-list-response.html)(accounts: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html)&gt;) |
| [prepareMockServerForTransactionListResponse](prepare-mock-server-for-transaction-list-response.html) | [androidJvm]<br>fun [prepareMockServerForTransactionListResponse](prepare-mock-server-for-transaction-list-response.html)(numberOfTransactionsForAccount: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), isPending: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; = listOf()) |
| [setUpIdlingRegistry](../../com.tink.moneymanagerui/-base-test-suite/set-up-idling-registry.html) | [androidJvm]<br>fun [setUpIdlingRegistry](../../com.tink.moneymanagerui/-base-test-suite/set-up-idling-registry.html)() |
| [tearDownIdlingRegistry](../../com.tink.moneymanagerui/-base-test-suite/tear-down-idling-registry.html) | [androidJvm]<br>fun [tearDownIdlingRegistry](../../com.tink.moneymanagerui/-base-test-suite/tear-down-idling-registry.html)() |
| [tearDownMockServer](../../com.tink.moneymanagerui/-base-test-suite/tear-down-mock-server.html) | [androidJvm]<br>fun [tearDownMockServer](../../com.tink.moneymanagerui/-base-test-suite/tear-down-mock-server.html)() |
| [tinkInit](../../com.tink.moneymanagerui/-base-test-suite/tink-init.html) | [androidJvm]<br>fun [tinkInit](../../com.tink.moneymanagerui/-base-test-suite/tink-init.html)() |


## Properties


| Name | Summary |
|---|---|
| [defaultAccount](default-account.html) | [androidJvm]<br>val [defaultAccount](default-account.html): [JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html) |
| [defaultAmount](default-amount.html) | [androidJvm]<br>val [defaultAmount](default-amount.html): Amount |
| [navigator](../../com.tink.moneymanagerui/-base-test-suite/navigator.html) | [androidJvm]<br>val [navigator](../../com.tink.moneymanagerui/-base-test-suite/navigator.html): [TestNavigator](../../com.tink.moneymanagerui.testutil/-test-navigator/index.html) |
| [resources](../../com.tink.moneymanagerui/-base-test-suite/resources.html) | [androidJvm]<br>val [resources](../../com.tink.moneymanagerui/-base-test-suite/resources.html): [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html) |
| [server](../../com.tink.moneymanagerui/-base-test-suite/server.html) | [androidJvm]<br>val [server](../../com.tink.moneymanagerui/-base-test-suite/server.html): &lt;Error class: unknown class&gt; |


## Inheritors


| Name |
|---|
| [AccountDetailsListTest](../-account-details-list-test/index.html) |
| [AccountOverviewListTest](../-account-overview-list-test/index.html) |
| [AccountTransactionListTest](../-account-transaction-list-test/index.html) |
| [EditAccountTest](../-edit-account-test/index.html) |
| [TransactionDetailsFragmentTest](../../com.tink.moneymanagerui.feature.transaction/-transaction-details-fragment-test/index.html) |
| [TransactionsListFragmentTest](../../com.tink.moneymanagerui.feature.transaction/-transactions-list-fragment-test/index.html) |

