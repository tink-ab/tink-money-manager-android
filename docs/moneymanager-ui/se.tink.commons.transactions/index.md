---
title: se.tink.commons.transactions
---
//[moneymanager-ui](../../index.html)/[se.tink.commons.transactions](index.html)



# Package se.tink.commons.transactions



## Types


| Name | Summary |
|---|---|
| [DateGroupViewHolder](-date-group-view-holder/index.html) | [androidJvm]<br>class [DateGroupViewHolder](-date-group-view-holder/index.html)(parent: [ViewGroup](https://developer.android.com/reference/kotlin/android/view/ViewGroup.html)) : [RecyclerView.ViewHolder](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.ViewHolder.html) |
| [ListItem](-list-item/index.html) | [androidJvm]<br>sealed class [ListItem](-list-item/index.html) |
| [Marked](index.html#943922129%2FClasslikes%2F1000845458) | [androidJvm]<br>typealias [Marked](index.html#943922129%2FClasslikes%2F1000845458) = [SimilarTransactionsAdapter.Marked](-similar-transactions-adapter/-marked/index.html) |
| [SimilarTransactionsAdapter](-similar-transactions-adapter/index.html) | [androidJvm]<br>class [SimilarTransactionsAdapter](-similar-transactions-adapter/index.html) : [RecyclerView.Adapter](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.Adapter.html)&lt;[SimilarTransactionsAdapter.SimilarTransactionViewHolder](-similar-transactions-adapter/-similar-transaction-view-holder/index.html)&gt; , [OnViewHolderClickedListener](../se.tink.android.viewholders/-on-view-holder-clicked-listener/index.html) |
| [TransactionItemFactory](-transaction-item-factory/index.html) | [androidJvm]<br>class [TransactionItemFactory](-transaction-item-factory/index.html)@Injectconstructor(amountFormatter: [AmountFormatter](../se.tink.commons.currency/-amount-formatter/index.html), dateUtils: [DateUtils](../se.tink.utils/-date-utils/index.html)) |
| [TransactionItemListAdapter](-transaction-item-list-adapter/index.html) | [androidJvm]<br>class [TransactionItemListAdapter](-transaction-item-list-adapter/index.html)(dateUtils: [DateUtils](../se.tink.utils/-date-utils/index.html)? = null, groupByDates: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true) : [RecyclerView.Adapter](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.Adapter.html)&lt;[RecyclerView.ViewHolder](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.ViewHolder.html)&gt; , [OnViewHolderClickedListener](../se.tink.android.viewholders/-on-view-holder-clicked-listener/index.html) |
| [TransactionItemViewHolder](-transaction-item-view-holder/index.html) | [androidJvm]<br>class [TransactionItemViewHolder](-transaction-item-view-holder/index.html)(parent: [ViewGroup](https://developer.android.com/reference/kotlin/android/view/ViewGroup.html), onViewHolderClickedListener: [OnViewHolderClickedListener](../se.tink.android.viewholders/-on-view-holder-clicked-listener/index.html)) : [ClickableViewHolder](../se.tink.android.viewholders/-clickable-view-holder/index.html) |
| [UpcomingHeaderViewHolder](-upcoming-header-view-holder/index.html) | [androidJvm]<br>class [UpcomingHeaderViewHolder](-upcoming-header-view-holder/index.html)(val parent: [ViewGroup](https://developer.android.com/reference/kotlin/android/view/ViewGroup.html), onToggleUpcomingTransactions: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)?) : [RecyclerView.ViewHolder](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.ViewHolder.html) |

