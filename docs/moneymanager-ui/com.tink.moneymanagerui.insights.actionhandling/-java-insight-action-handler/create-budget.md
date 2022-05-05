---
title: createBudget
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.insights.actionhandling](../index.html)/[JavaInsightActionHandler](index.html)/[createBudget](create-budget.html)



# createBudget



[androidJvm]\
open fun [createBudget](create-budget.html)(amount: [Amount](../../com.tink.model.misc/-amount/index.html)?, filter: [BudgetFilter](../../com.tink.model.budget/index.html#-2018963458%2FClasslikes%2F1000845458)?, periodicity: [BudgetPeriodicity](../../com.tink.model.budget/index.html#-756637127%2FClasslikes%2F1000845458)?, onHandled: [OnInsightHandled](../../com.tink.moneymanagerui.insights.actionhandling.callbacks/-on-insight-handled/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)



Handle action where the user wants to create a budget. This corresponds to [InsightAction.Type.CREATE_BUDGET](../../com.tink.model.insights/-insight-action/-type/-c-r-e-a-t-e_-b-u-d-g-e-t/index.html) action type.



#### Return



true if the action will be handled, false otherwise



## Parameters


androidJvm

| | |
|---|---|
| amount | An optional budget amount to be set |
| filter | An optional [BudgetFilter](../../com.tink.model.budget/index.html#-2018963458%2FClasslikes%2F1000845458) to be set |
| periodicity | An optional [BudgetPeriodicity](../../com.tink.model.budget/index.html#-756637127%2FClasslikes%2F1000845458) to be set |
| onHandled | The [OnInsightHandled](../../com.tink.moneymanagerui.insights.actionhandling.callbacks/-on-insight-handled/index.html) to invoke to indicate if the action has completed successfully or not |




