---
title: viewBudget
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.insights.actionhandling](../index.html)/[JavaInsightActionHandler](index.html)/[viewBudget](view-budget.html)



# viewBudget



[androidJvm]\
open fun [viewBudget](view-budget.html)(budgetId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), periodStart: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)



Handle action where the user wants to view information for a budget matching the id and start period. This corresponds to [InsightAction.Type.VIEW_BUDGET](../../com.tink.model.insights/-insight-action/-type/-v-i-e-w_-b-u-d-g-e-t/index.html) action type.



#### Return



true if the action will be handled, false otherwise



## Parameters


androidJvm

| | |
|---|---|
| budgetId | Identifier for the budget that should be shown |
| periodStart | The start period for the budget that should be shown |




