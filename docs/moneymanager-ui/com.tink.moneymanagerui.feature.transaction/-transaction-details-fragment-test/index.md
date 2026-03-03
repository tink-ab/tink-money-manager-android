---
title: TransactionDetailsFragmentTest
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.feature.transaction](../index.html)/[TransactionDetailsFragmentTest](index.html)



# TransactionDetailsFragmentTest



[androidJvm]\
class [TransactionDetailsFragmentTest](index.html) : [BaseAccountTestSuit](../../com.tink.moneymanagerui.feature.account/-base-account-test-suit/index.html)



## Constructors


| | |
|---|---|
| [TransactionDetailsFragmentTest](-transaction-details-fragment-test.html) | [androidJvm]<br>fun [TransactionDetailsFragmentTest](-transaction-details-fragment-test.html)() |


## Functions


| Name | Summary |
|---|---|
| [navigate_to_transaction_details_from_account_details_page](navigate_to_transaction_details_from_account_details_page.html) | [androidJvm]<br>fun [navigate_to_transaction_details_from_account_details_page](navigate_to_transaction_details_from_account_details_page.html)() |
| [navigate_to_transaction_details_from_all_transactions_page](navigate_to_transaction_details_from_all_transactions_page.html) | [androidJvm]<br>fun [navigate_to_transaction_details_from_all_transactions_page](navigate_to_transaction_details_from_all_transactions_page.html)() |
| [navigate_to_transaction_details_from_overview_latest_transactions](navigate_to_transaction_details_from_overview_latest_transactions.html) | [androidJvm]<br>fun [navigate_to_transaction_details_from_overview_latest_transactions](navigate_to_transaction_details_from_overview_latest_transactions.html)() |
| [openAccountDetailsFromOverview](../../com.tink.moneymanagerui.feature.account/-base-account-test-suit/open-account-details-from-overview.html) | [androidJvm]<br>fun [openAccountDetailsFromOverview](../../com.tink.moneymanagerui.feature.account/-base-account-test-suit/open-account-details-from-overview.html)(overviewAccountsMode: [OverviewAccountsMode](../../com.tink.moneymanagerui.accounts/-overview-accounts-mode/index.html) = OverviewFavoriteAccounts, accountGroupType: [AccountGroupType](../../com.tink.moneymanagerui.accounts/-account-group-type/index.html) = NoAccountGroup, accountEditConfiguration: [AccountEditConfiguration](../../com.tink.moneymanagerui.accounts/-account-edit-configuration/index.html) = AccountEditConfiguration.ALL, isEditableOnPendingTrx: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) |
| [openTransactionDetailsFromAccountDetails](../../com.tink.moneymanagerui.feature.account/-base-account-test-suit/open-transaction-details-from-account-details.html) | [androidJvm]<br>fun [openTransactionDetailsFromAccountDetails](../../com.tink.moneymanagerui.feature.account/-base-account-test-suit/open-transaction-details-from-account-details.html)() |
| [prepareMockServerForAccountListResponse](../../com.tink.moneymanagerui.feature.account/-base-account-test-suit/prepare-mock-server-for-account-list-response.html) | [androidJvm]<br>fun [prepareMockServerForAccountListResponse](../../com.tink.moneymanagerui.feature.account/-base-account-test-suit/prepare-mock-server-for-account-list-response.html)(accounts: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html)&gt;) |
| [prepareMockServerForTransactionListResponse](../../com.tink.moneymanagerui.feature.account/-base-account-test-suit/prepare-mock-server-for-transaction-list-response.html) | [androidJvm]<br>fun [prepareMockServerForTransactionListResponse](../../com.tink.moneymanagerui.feature.account/-base-account-test-suit/prepare-mock-server-for-transaction-list-response.html)(numberOfTransactionsForAccount: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), isPending: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; = listOf()) |
| [setUpIdlingRegistry](../../com.tink.moneymanagerui/-base-test-suite/set-up-idling-registry.html) | [androidJvm]<br>fun [setUpIdlingRegistry](../../com.tink.moneymanagerui/-base-test-suite/set-up-idling-registry.html)() |
| [tearDownIdlingRegistry](../../com.tink.moneymanagerui/-base-test-suite/tear-down-idling-registry.html) | [androidJvm]<br>fun [tearDownIdlingRegistry](../../com.tink.moneymanagerui/-base-test-suite/tear-down-idling-registry.html)() |
| [tearDownMockServer](../../com.tink.moneymanagerui/-base-test-suite/tear-down-mock-server.html) | [androidJvm]<br>fun [tearDownMockServer](../../com.tink.moneymanagerui/-base-test-suite/tear-down-mock-server.html)() |
| [tinkInit](../../com.tink.moneymanagerui/-base-test-suite/tink-init.html) | [androidJvm]<br>fun [tinkInit](../../com.tink.moneymanagerui/-base-test-suite/tink-init.html)() |
| [transaction_details_should_back_arrow_to_navigate_back](transaction_details_should_back_arrow_to_navigate_back.html) | [androidJvm]<br>fun [transaction_details_should_back_arrow_to_navigate_back](transaction_details_should_back_arrow_to_navigate_back.html)() |
| [transaction_details_should_show_correct_color_for_category_icon_for_non_pending_transaction](transaction_details_should_show_correct_color_for_category_icon_for_non_pending_transaction.html) | [androidJvm]<br>fun [transaction_details_should_show_correct_color_for_category_icon_for_non_pending_transaction](transaction_details_should_show_correct_color_for_category_icon_for_non_pending_transaction.html)() |
| [transaction_details_should_show_correct_color_for_header](transaction_details_should_show_correct_color_for_header.html) | [androidJvm]<br>fun [transaction_details_should_show_correct_color_for_header](transaction_details_should_show_correct_color_for_header.html)() |
| [transaction_details_should_show_correct_icon_for_pending_transaction](transaction_details_should_show_correct_icon_for_pending_transaction.html) | [androidJvm]<br>fun [transaction_details_should_show_correct_icon_for_pending_transaction](transaction_details_should_show_correct_icon_for_pending_transaction.html)() |
| [transaction_details_should_show_edit_category_icon_only_for_editable_transactions](transaction_details_should_show_edit_category_icon_only_for_editable_transactions.html) | [androidJvm]<br>fun [transaction_details_should_show_edit_category_icon_only_for_editable_transactions](transaction_details_should_show_edit_category_icon_only_for_editable_transactions.html)() |
| [transaction_details_should_show_edit_transaction_icon_only_for_editable_transactions](transaction_details_should_show_edit_transaction_icon_only_for_editable_transactions.html) | [androidJvm]<br>fun [transaction_details_should_show_edit_transaction_icon_only_for_editable_transactions](transaction_details_should_show_edit_transaction_icon_only_for_editable_transactions.html)() |
| [transaction_details_should_show_transaction_data_in_header](transaction_details_should_show_transaction_data_in_header.html) | [androidJvm]<br>fun [transaction_details_should_show_transaction_data_in_header](transaction_details_should_show_transaction_data_in_header.html)() |


## Properties


| Name | Summary |
|---|---|
| [defaultAccount](../../com.tink.moneymanagerui.feature.account/-base-account-test-suit/default-account.html) | [androidJvm]<br>val [defaultAccount](../../com.tink.moneymanagerui.feature.account/-base-account-test-suit/default-account.html): [JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html) |
| [defaultAmount](../../com.tink.moneymanagerui.feature.account/-base-account-test-suit/default-amount.html) | [androidJvm]<br>val [defaultAmount](../../com.tink.moneymanagerui.feature.account/-base-account-test-suit/default-amount.html): Amount |
| [navigator](../../com.tink.moneymanagerui/-base-test-suite/navigator.html) | [androidJvm]<br>val [navigator](../../com.tink.moneymanagerui/-base-test-suite/navigator.html): [TestNavigator](../../com.tink.moneymanagerui.testutil/-test-navigator/index.html) |
| [resources](../../com.tink.moneymanagerui/-base-test-suite/resources.html) | [androidJvm]<br>val [resources](../../com.tink.moneymanagerui/-base-test-suite/resources.html): [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html) |
| [server](../../com.tink.moneymanagerui/-base-test-suite/server.html) | [androidJvm]<br>val [server](../../com.tink.moneymanagerui/-base-test-suite/server.html): &lt;Error class: unknown class&gt; |

