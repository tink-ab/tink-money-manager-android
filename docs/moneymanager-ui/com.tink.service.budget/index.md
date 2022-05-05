---
title: com.tink.service.budget
---
//[moneymanager-ui](../../index.html)/[com.tink.service.budget](index.html)



# Package com.tink.service.budget



## Types


| Name | Summary |
|---|---|
| [BudgetService](-budget-service/index.html) | [androidJvm]<br>interface [BudgetService](-budget-service/index.html) |
| [BudgetServiceImpl](-budget-service-impl/index.html) | [androidJvm]<br>class [BudgetServiceImpl](-budget-service-impl/index.html)@Injectconstructor(val api: BudgetApi) : [BudgetService](-budget-service/index.html) |


## Functions


| Name | Summary |
|---|---|
| [toCoreModel](to-core-model.html) | [androidJvm]<br>fun Budget.[toCoreModel](to-core-model.html)(): [BudgetSpecification](../com.tink.model.budget/index.html#1357535401%2FClasslikes%2F1000845458)<br>fun BudgetSummary.[toCoreModel](to-core-model.html)(): [Budget.Summary](../com.tink.model.budget/-budget/-summary/index.html)<br>fun BudgetTransaction.[toCoreModel](to-core-model.html)(): [Budget.Transaction](../com.tink.model.budget/-budget/-transaction/index.html)<br>fun Filter.[toCoreModel](to-core-model.html)(): [Budget.Specification.Filter](../com.tink.model.budget/-budget/-specification/-filter/index.html)<br>fun BudgetPeriod.[toCoreModel](to-core-model.html)(budgetAmount: [Amount](../com.tink.model.misc/-amount/index.html)): [Budget.Period](../com.tink.model.budget/-budget/-period/index.html) |
| [toDto](to-dto.html) | [androidJvm]<br>fun [OneOffPeriodicity](../com.tink.model.budget/index.html#668268061%2FClasslikes%2F1000845458).[toDto](to-dto.html)(): OneOffPeriodicity<br>fun [RecurringPeriodicity](../com.tink.model.budget/index.html#-420361691%2FClasslikes%2F1000845458).[toDto](to-dto.html)(): RecurringPeriodicity<br>fun [Budget.Specification.Filter](../com.tink.model.budget/-budget/-specification/-filter/index.html).[toDto](to-dto.html)(): Filter |

