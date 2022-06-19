---
title: TransactionItemFactory
---
//[moneymanager-ui](../../../index.html)/[se.tink.commons.transactions](../index.html)/[TransactionItemFactory](index.html)



# TransactionItemFactory



[androidJvm]\
class [TransactionItemFactory](index.html)@Injectconstructor(amountFormatter: [AmountFormatter](../../se.tink.commons.currency/-amount-formatter/index.html), dateUtils: [DateUtils](../../se.tink.utils/-date-utils/index.html))



## Constructors


| | |
|---|---|
| [TransactionItemFactory](-transaction-item-factory.html) | [androidJvm]<br>@Inject<br>fun [TransactionItemFactory](-transaction-item-factory.html)(amountFormatter: [AmountFormatter](../../se.tink.commons.currency/-amount-formatter/index.html), dateUtils: [DateUtils](../../se.tink.utils/-date-utils/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [createItem](create-item.html) | [androidJvm]<br>fun [createItem](create-item.html)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), isUpcoming: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), category: [Category](../../com.tink.model.category/-category/index.html), date: [LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html), description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), label: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amount: [Amount](../../com.tink.model.misc/-amount/index.html), dispensableAmount: [Amount](../../com.tink.model.misc/-amount/index.html) = Amount(ExactNumber(0, 0), &quot;SEK&quot;), upcomingTransactionData: [ListItem.TransactionItem.UpcomingTransactionData](../-list-item/-transaction-item/-upcoming-transaction-data/index.html)? = null, isPending: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), isEditable: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)): [ListItem.TransactionItem](../-list-item/-transaction-item/index.html)? |
| [fromTransaction](from-transaction.html) | [androidJvm]<br>fun [fromTransaction](from-transaction.html)(transaction: [Transaction](../../com.tink.model.transaction/-transaction/index.html), category: [Category](../../com.tink.model.category/-category/index.html), isEditable: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)): [ListItem.TransactionItem](../-list-item/-transaction-item/index.html)? |
| [latestTransactionItemFromTransactionItem](latest-transaction-item-from-transaction-item.html) | [androidJvm]<br>fun [latestTransactionItemFromTransactionItem](latest-transaction-item-from-transaction-item.html)(transactionItem: [ListItem.TransactionItem](../-list-item/-transaction-item/index.html)): [ListItem.TransactionItem](../-list-item/-transaction-item/index.html) |
| [similarTransactionItemFromTransaction](similar-transaction-item-from-transaction.html) | [androidJvm]<br>fun [similarTransactionItemFromTransaction](similar-transaction-item-from-transaction.html)(transaction: [Transaction](../../com.tink.model.transaction/-transaction/index.html), category: [Category](../../com.tink.model.category/-category/index.html), isSelected: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, isEditable: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true): [SimilarTransactionsAdapter.SimilarTransactionItem](../-similar-transactions-adapter/-similar-transaction-item/index.html)? |

