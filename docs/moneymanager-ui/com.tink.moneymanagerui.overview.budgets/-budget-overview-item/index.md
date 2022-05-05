---
title: BudgetOverviewItem
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.overview.budgets](../index.html)/[BudgetOverviewItem](index.html)



# BudgetOverviewItem



[androidJvm]\
data class [BudgetOverviewItem](index.html)(val budgetId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val icon: [BudgetOverviewItem.Icon](-icon/index.html), val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val periodLabel: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val progress: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val progressMax: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val budgetAmount: [Amount](../../com.tink.model.misc/-amount/index.html), val spentAmount: [Amount](../../com.tink.model.misc/-amount/index.html))



## Constructors


| | |
|---|---|
| [BudgetOverviewItem](-budget-overview-item.html) | [androidJvm]<br>fun [BudgetOverviewItem](-budget-overview-item.html)(budgetId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), icon: [BudgetOverviewItem.Icon](-icon/index.html), name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), periodLabel: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), progress: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), progressMax: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), budgetAmount: [Amount](../../com.tink.model.misc/-amount/index.html), spentAmount: [Amount](../../com.tink.model.misc/-amount/index.html)) |


## Types


| Name | Summary |
|---|---|
| [Icon](-icon/index.html) | [androidJvm]<br>data class [Icon](-icon/index.html)(@[AttrRes](https://developer.android.com/reference/kotlin/androidx/annotation/AttrRes.html)val resource: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), @[AttrRes](https://developer.android.com/reference/kotlin/androidx/annotation/AttrRes.html)val color: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), @[AttrRes](https://developer.android.com/reference/kotlin/androidx/annotation/AttrRes.html)val backgroundColor: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [isContentTheSame](is-content-the-same.html) | [androidJvm]<br>fun [isContentTheSame](is-content-the-same.html)(other: [BudgetOverviewItem](index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isSameItem](is-same-item.html) | [androidJvm]<br>fun [isSameItem](is-same-item.html)(other: [BudgetOverviewItem](index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |


## Properties


| Name | Summary |
|---|---|
| [budgetAmount](budget-amount.html) | [androidJvm]<br>val [budgetAmount](budget-amount.html): [Amount](../../com.tink.model.misc/-amount/index.html) |
| [budgetId](budget-id.html) | [androidJvm]<br>val [budgetId](budget-id.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [icon](icon.html) | [androidJvm]<br>val [icon](icon.html): [BudgetOverviewItem.Icon](-icon/index.html) |
| [name](name.html) | [androidJvm]<br>val [name](name.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [periodLabel](period-label.html) | [androidJvm]<br>val [periodLabel](period-label.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [progress](progress.html) | [androidJvm]<br>val [progress](progress.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [progressMax](progress-max.html) | [androidJvm]<br>val [progressMax](progress-max.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [spentAmount](spent-amount.html) | [androidJvm]<br>val [spentAmount](spent-amount.html): [Amount](../../com.tink.model.misc/-amount/index.html) |

