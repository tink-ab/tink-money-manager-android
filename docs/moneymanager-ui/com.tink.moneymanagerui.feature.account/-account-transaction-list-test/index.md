---
title: AccountTransactionListTest
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.feature.account](../index.html)/[AccountTransactionListTest](index.html)



# AccountTransactionListTest



[androidJvm]\
class [AccountTransactionListTest](index.html) : [BaseAccountTestSuit](../-base-account-test-suit/index.html)



## Constructors


| | |
|---|---|
| [AccountTransactionListTest](-account-transaction-list-test.html) | [androidJvm]<br>fun [AccountTransactionListTest](-account-transaction-list-test.html)() |


## Functions


| Name | Summary |
|---|---|
| [openAccountDetailsFromOverview](../-base-account-test-suit/open-account-details-from-overview.html) | [androidJvm]<br>fun [openAccountDetailsFromOverview](../-base-account-test-suit/open-account-details-from-overview.html)(overviewAccountsMode: [OverviewAccountsMode](../../com.tink.moneymanagerui.accounts/-overview-accounts-mode/index.html) = OverviewFavoriteAccounts, accountGroupType: [AccountGroupType](../../com.tink.moneymanagerui.accounts/-account-group-type/index.html) = NoAccountGroup, accountEditConfiguration: [AccountEditConfiguration](../../com.tink.moneymanagerui.accounts/-account-edit-configuration/index.html) = AccountEditConfiguration.ALL, isEditableOnPendingTrx: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) |
| [openTransactionDetailsFromAccountDetails](../-base-account-test-suit/open-transaction-details-from-account-details.html) | [androidJvm]<br>fun [openTransactionDetailsFromAccountDetails](../-base-account-test-suit/open-transaction-details-from-account-details.html)() |
| [prepareMockServerForAccountListResponse](../-base-account-test-suit/prepare-mock-server-for-account-list-response.html) | [androidJvm]<br>fun [prepareMockServerForAccountListResponse](../-base-account-test-suit/prepare-mock-server-for-account-list-response.html)(accounts: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html)&gt;) |
| [prepareMockServerForTransactionListResponse](../-base-account-test-suit/prepare-mock-server-for-transaction-list-response.html) | [androidJvm]<br>fun [prepareMockServerForTransactionListResponse](../-base-account-test-suit/prepare-mock-server-for-transaction-list-response.html)(numberOfTransactionsForAccount: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), isPending: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; = listOf()) |
| [setUpIdlingRegistry](../../com.tink.moneymanagerui/-base-test-suite/set-up-idling-registry.html) | [androidJvm]<br>fun [setUpIdlingRegistry](../../com.tink.moneymanagerui/-base-test-suite/set-up-idling-registry.html)() |
| [shows_account_number](shows_account_number.html) | [androidJvm]<br>fun [shows_account_number](shows_account_number.html)() |
| [shows_balance](shows_balance.html) | [androidJvm]<br>fun [shows_balance](shows_balance.html)() |
| [shows_toolbar_title](shows_toolbar_title.html) | [androidJvm]<br>fun [shows_toolbar_title](shows_toolbar_title.html)() |
| [shows_transactions_in_list](shows_transactions_in_list.html) | [androidJvm]<br>fun [shows_transactions_in_list](shows_transactions_in_list.html)() |
| [tearDownIdlingRegistry](../../com.tink.moneymanagerui/-base-test-suite/tear-down-idling-registry.html) | [androidJvm]<br>fun [tearDownIdlingRegistry](../../com.tink.moneymanagerui/-base-test-suite/tear-down-idling-registry.html)() |
| [tearDownMockServer](../../com.tink.moneymanagerui/-base-test-suite/tear-down-mock-server.html) | [androidJvm]<br>fun [tearDownMockServer](../../com.tink.moneymanagerui/-base-test-suite/tear-down-mock-server.html)() |
| [tinkInit](../../com.tink.moneymanagerui/-base-test-suite/tink-init.html) | [androidJvm]<br>fun [tinkInit](../../com.tink.moneymanagerui/-base-test-suite/tink-init.html)() |


## Properties


| Name | Summary |
|---|---|
| [defaultAccount](../-base-account-test-suit/default-account.html) | [androidJvm]<br>val [defaultAccount](../-base-account-test-suit/default-account.html): [JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html) |
| [defaultAmount](../-base-account-test-suit/default-amount.html) | [androidJvm]<br>val [defaultAmount](../-base-account-test-suit/default-amount.html): Amount |
| [navigator](../../com.tink.moneymanagerui/-base-test-suite/navigator.html) | [androidJvm]<br>val [navigator](../../com.tink.moneymanagerui/-base-test-suite/navigator.html): [TestNavigator](../../com.tink.moneymanagerui.testutil/-test-navigator/index.html) |
| [resources](../../com.tink.moneymanagerui/-base-test-suite/resources.html) | [androidJvm]<br>val [resources](../../com.tink.moneymanagerui/-base-test-suite/resources.html): [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html) |
| [server](../../com.tink.moneymanagerui/-base-test-suite/server.html) | [androidJvm]<br>val [server](../../com.tink.moneymanagerui/-base-test-suite/server.html): &lt;Error class: unknown class&gt; |

