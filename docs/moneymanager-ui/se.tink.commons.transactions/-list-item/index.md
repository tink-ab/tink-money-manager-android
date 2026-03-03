---
title: ListItem
---
//[moneymanager-ui](../../../index.html)/[se.tink.commons.transactions](../index.html)/[ListItem](index.html)



# ListItem



[androidJvm]\
sealed class [ListItem](index.html)



## Types


| Name | Summary |
|---|---|
| [DateGroupItem](-date-group-item/index.html) | [androidJvm]<br>data class [DateGroupItem](-date-group-item/index.html)(val date: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [ListItem](index.html) |
| [TransactionItem](-transaction-item/index.html) | [androidJvm]<br>data class [TransactionItem](-transaction-item/index.html)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val icon: [ListItem.TransactionItem.Icon](-transaction-item/-icon/index.html), val label: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val amount: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val dispensableAmount: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val date: [LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html), val merchantLogoAllowed: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), val recurring: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val upcomingTransactionData: [ListItem.TransactionItem.UpcomingTransactionData](-transaction-item/-upcoming-transaction-data/index.html)? = null, val isPending: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), val isEditable: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) : [ListItem](index.html) |
| [UpcomingGroupHeaderItem](-upcoming-group-header-item/index.html) | [androidJvm]<br>data class [UpcomingGroupHeaderItem](-upcoming-group-header-item/index.html)(val title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val expanded: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) : [ListItem](index.html) |
| [ViewType](-view-type/index.html) | [androidJvm]<br>enum [ViewType](-view-type/index.html) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[ListItem.ViewType](-view-type/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [getViewType](get-view-type.html) | [androidJvm]<br>abstract fun [getViewType](get-view-type.html)(): [ListItem.ViewType](-view-type/index.html) |
| [isContentTheSame](is-content-the-same.html) | [androidJvm]<br>open fun [isContentTheSame](is-content-the-same.html)(other: [ListItem](index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isSameItem](is-same-item.html) | [androidJvm]<br>abstract fun [isSameItem](is-same-item.html)(other: [ListItem](index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |


## Inheritors


| Name |
|---|
| [TransactionItem](-transaction-item/index.html) |
| [DateGroupItem](-date-group-item/index.html) |
| [UpcomingGroupHeaderItem](-upcoming-group-header-item/index.html) |

