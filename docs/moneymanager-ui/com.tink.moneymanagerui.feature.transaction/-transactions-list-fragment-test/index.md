---
title: TransactionsListFragmentTest
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.feature.transaction](../index.html)/[TransactionsListFragmentTest](index.html)



# TransactionsListFragmentTest



[androidJvm]\
class [TransactionsListFragmentTest](index.html) : [BaseAccountTestSuit](../../com.tink.moneymanagerui.feature.account/-base-account-test-suit/index.html)



## Constructors


| | |
|---|---|
| [TransactionsListFragmentTest](-transactions-list-fragment-test.html) | [androidJvm]<br>fun [TransactionsListFragmentTest](-transactions-list-fragment-test.html)() |


## Functions


| Name | Summary |
|---|---|
| [openAccountDetailsFromOverview](../../com.tink.moneymanagerui.feature.account/-base-account-test-suit/open-account-details-from-overview.html) | [androidJvm]<br>fun [openAccountDetailsFromOverview](../../com.tink.moneymanagerui.feature.account/-base-account-test-suit/open-account-details-from-overview.html)(overviewAccountsMode: [OverviewAccountsMode](../../com.tink.moneymanagerui.accounts/-overview-accounts-mode/index.html) = OverviewFavoriteAccounts, accountGroupType: [AccountGroupType](../../com.tink.moneymanagerui.accounts/-account-group-type/index.html) = NoAccountGroup, accountEditConfiguration: [AccountEditConfiguration](../../com.tink.moneymanagerui.accounts/-account-edit-configuration/index.html) = AccountEditConfiguration.ALL, isEditableOnPendingTrx: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) |
| [openTransactionDetailsFromAccountDetails](../../com.tink.moneymanagerui.feature.account/-base-account-test-suit/open-transaction-details-from-account-details.html) | [androidJvm]<br>fun [openTransactionDetailsFromAccountDetails](../../com.tink.moneymanagerui.feature.account/-base-account-test-suit/open-transaction-details-from-account-details.html)() |
| [prepareMockServerForAccountListResponse](../../com.tink.moneymanagerui.feature.account/-base-account-test-suit/prepare-mock-server-for-account-list-response.html) | [androidJvm]<br>fun [prepareMockServerForAccountListResponse](../../com.tink.moneymanagerui.feature.account/-base-account-test-suit/prepare-mock-server-for-account-list-response.html)(accounts: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html)&gt;) |
| [prepareMockServerForTransactionListResponse](../../com.tink.moneymanagerui.feature.account/-base-account-test-suit/prepare-mock-server-for-transaction-list-response.html) | [androidJvm]<br>fun [prepareMockServerForTransactionListResponse](../../com.tink.moneymanagerui.feature.account/-base-account-test-suit/prepare-mock-server-for-transaction-list-response.html)(numberOfTransactionsForAccount: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), isPending: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; = listOf()) |
| [setUpIdlingRegistry](../../com.tink.moneymanagerui/-base-test-suite/set-up-idling-registry.html) | [androidJvm]<br>fun [setUpIdlingRegistry](../../com.tink.moneymanagerui/-base-test-suite/set-up-idling-registry.html)() |
| [tearDownIdlingRegistry](../../com.tink.moneymanagerui/-base-test-suite/tear-down-idling-registry.html) | [androidJvm]<br>fun [tearDownIdlingRegistry](../../com.tink.moneymanagerui/-base-test-suite/tear-down-idling-registry.html)() |
| [tearDownMockServer](../../com.tink.moneymanagerui/-base-test-suite/tear-down-mock-server.html) | [androidJvm]<br>fun [tearDownMockServer](../../com.tink.moneymanagerui/-base-test-suite/tear-down-mock-server.html)() |
| [test_is_pending_editable_transaction_open_transaction_details](test_is_pending_editable_transaction_open_transaction_details.html) | [androidJvm]<br>fun [test_is_pending_editable_transaction_open_transaction_details](test_is_pending_editable_transaction_open_transaction_details.html)() |
| [test_is_pending_editable_transaction_visible](test_is_pending_editable_transaction_visible.html) | [androidJvm]<br>fun [test_is_pending_editable_transaction_visible](test_is_pending_editable_transaction_visible.html)() |
| [test_is_pending_uneditable_transaction_open_dialog_msg](test_is_pending_uneditable_transaction_open_dialog_msg.html) | [androidJvm]<br>fun [test_is_pending_uneditable_transaction_open_dialog_msg](test_is_pending_uneditable_transaction_open_dialog_msg.html)() |
| [test_is_pending_uneditable_transaction_visible](test_is_pending_uneditable_transaction_visible.html) | [androidJvm]<br>fun [test_is_pending_uneditable_transaction_visible](test_is_pending_uneditable_transaction_visible.html)() |
| [test_isnotpending_editable_transaction_visible](test_isnotpending_editable_transaction_visible.html) | [androidJvm]<br>fun [test_isnotpending_editable_transaction_visible](test_isnotpending_editable_transaction_visible.html)() |
| [test_isnotpending_uneditable_transaction_visible](test_isnotpending_uneditable_transaction_visible.html) | [androidJvm]<br>fun [test_isnotpending_uneditable_transaction_visible](test_isnotpending_uneditable_transaction_visible.html)() |
| [test_pending_transactions_in_scrollable_list](test_pending_transactions_in_scrollable_list.html) | [androidJvm]<br>fun [test_pending_transactions_in_scrollable_list](test_pending_transactions_in_scrollable_list.html)() |
| [tinkInit](../../com.tink.moneymanagerui/-base-test-suite/tink-init.html) | [androidJvm]<br>fun [tinkInit](../../com.tink.moneymanagerui/-base-test-suite/tink-init.html)() |


## Properties


| Name | Summary |
|---|---|
| [defaultAccount](../../com.tink.moneymanagerui.feature.account/-base-account-test-suit/default-account.html) | [androidJvm]<br>val [defaultAccount](../../com.tink.moneymanagerui.feature.account/-base-account-test-suit/default-account.html): [JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html) |
| [defaultAmount](../../com.tink.moneymanagerui.feature.account/-base-account-test-suit/default-amount.html) | [androidJvm]<br>val [defaultAmount](../../com.tink.moneymanagerui.feature.account/-base-account-test-suit/default-amount.html): Amount |
| [navigator](../../com.tink.moneymanagerui/-base-test-suite/navigator.html) | [androidJvm]<br>val [navigator](../../com.tink.moneymanagerui/-base-test-suite/navigator.html): [TestNavigator](../../com.tink.moneymanagerui.testutil/-test-navigator/index.html) |
| [resources](../../com.tink.moneymanagerui/-base-test-suite/resources.html) | [androidJvm]<br>val [resources](../../com.tink.moneymanagerui/-base-test-suite/resources.html): [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html) |
| [server](../../com.tink.moneymanagerui/-base-test-suite/server.html) | [androidJvm]<br>val [server](../../com.tink.moneymanagerui/-base-test-suite/server.html): &lt;Error class: unknown class&gt; |

