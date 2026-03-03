---
title: com.tink.moneymanagerui.budgets.details.model
---
//[moneymanager-ui](../../index.html)/[com.tink.moneymanagerui.budgets.details.model](index.html)



# Package com.tink.moneymanagerui.budgets.details.model



## Types


| Name | Summary |
|---|---|
| [BudgetSelectionData](-budget-selection-data/index.html) | [androidJvm]<br>data class [BudgetSelectionData](-budget-selection-data/index.html)(val budget: BudgetSpecification, val budgetPeriodsList: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;BudgetPeriod&gt;, val currentSelectedPeriod: BudgetPeriod) |
| [BudgetSelectionState](-budget-selection-state/index.html) | [androidJvm]<br>data class [BudgetSelectionState](-budget-selection-state/index.html)(val budget: ResponseState&lt;BudgetSpecification&gt; = LoadingState, val budgetPeriodsList: ResponseState&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;BudgetPeriod&gt;&gt; = LoadingState, val currentSelectedPeriod: ResponseState&lt;BudgetPeriod&gt; = LoadingState) |

