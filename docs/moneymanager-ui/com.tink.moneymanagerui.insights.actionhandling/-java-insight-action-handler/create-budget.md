---
title: createBudget
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.insights.actionhandling](../index.html)/[JavaInsightActionHandler](index.html)/[createBudget](create-budget.html)



# createBudget



[androidJvm]\
open fun [createBudget](create-budget.html)(amount: Amount?, filter: BudgetFilter?, periodicity: BudgetPeriodicity?, onHandled: [OnInsightHandled](../../com.tink.moneymanagerui.insights.actionhandling.callbacks/-on-insight-handled/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)



Handle action where the user wants to create a budget. This corresponds to InsightAction.Type.CREATE_BUDGET action type.



#### Return



`true` if the action will be handled, `false` otherwise



## Parameters


androidJvm

| | |
|---|---|
| amount | An optional budget amount to be set |
| filter | An optional BudgetFilter to be set |
| periodicity | An optional BudgetPeriodicity to be set |
| onHandled | The [OnInsightHandled](../../com.tink.moneymanagerui.insights.actionhandling.callbacks/-on-insight-handled/index.html) to invoke to indicate if the action has completed successfully or not |




