---
title: TransactionItem
---
//[moneymanager-ui](../../../../index.html)/[se.tink.commons.transactions](../../index.html)/[ListItem](../index.html)/[TransactionItem](index.html)



# TransactionItem



[androidJvm]\
data class [TransactionItem](index.html)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val icon: [ListItem.TransactionItem.Icon](-icon/index.html), val label: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val amount: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val dispensableAmount: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val date: DateTime, val merchantLogoAllowed: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), val recurring: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val upcomingTransactionData: [ListItem.TransactionItem.UpcomingTransactionData](-upcoming-transaction-data/index.html)? = null) : [ListItem](../index.html)



## Constructors


| | |
|---|---|
| [TransactionItem](-transaction-item.html) | [androidJvm]<br>fun [TransactionItem](-transaction-item.html)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), icon: [ListItem.TransactionItem.Icon](-icon/index.html), label: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amount: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), dispensableAmount: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), date: DateTime, merchantLogoAllowed: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), recurring: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, upcomingTransactionData: [ListItem.TransactionItem.UpcomingTransactionData](-upcoming-transaction-data/index.html)? = null) |


## Types


| Name | Summary |
|---|---|
| [Icon](-icon/index.html) | [androidJvm]<br>data class [Icon](-icon/index.html)(val resource: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val color: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val backgroundColor: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [UpcomingTransactionData](-upcoming-transaction-data/index.html) | [androidJvm]<br>data class [UpcomingTransactionData](-upcoming-transaction-data/index.html)(val pending: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), val editable: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), val transferId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [getViewType](get-view-type.html) | [androidJvm]<br>open override fun [getViewType](get-view-type.html)(): [ListItem.ViewType](../-view-type/index.html) |
| [isContentTheSame](../is-content-the-same.html) | [androidJvm]<br>open fun [isContentTheSame](../is-content-the-same.html)(other: [ListItem](../index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isSameItem](is-same-item.html) | [androidJvm]<br>open override fun [isSameItem](is-same-item.html)(other: [ListItem](../index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |


## Properties


| Name | Summary |
|---|---|
| [amount](amount.html) | [androidJvm]<br>val [amount](amount.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [date](date.html) | [androidJvm]<br>val [date](date.html): DateTime |
| [description](description.html) | [androidJvm]<br>val [description](description.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [dispensableAmount](dispensable-amount.html) | [androidJvm]<br>val [dispensableAmount](dispensable-amount.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [icon](icon.html) | [androidJvm]<br>val [icon](icon.html): [ListItem.TransactionItem.Icon](-icon/index.html) |
| [id](id.html) | [androidJvm]<br>val [id](id.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [label](label.html) | [androidJvm]<br>val [label](label.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [merchantLogoAllowed](merchant-logo-allowed.html) | [androidJvm]<br>val [merchantLogoAllowed](merchant-logo-allowed.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [recurring](recurring.html) | [androidJvm]<br>val [recurring](recurring.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
| [upcomingTransactionData](upcoming-transaction-data.html) | [androidJvm]<br>val [upcomingTransactionData](upcoming-transaction-data.html): [ListItem.TransactionItem.UpcomingTransactionData](-upcoming-transaction-data/index.html)? = null |

