---
title: BudgetsRepository
---
//[moneymanager-ui](../../../index.html)/[se.tink.android.repository.budget](../index.html)/[BudgetsRepository](index.html)



# BudgetsRepository



[androidJvm]\
@ExperimentalCoroutinesApi



class [BudgetsRepository](index.html)@Injectconstructor(budgetService: [BudgetService](../../com.tink.service.budget/-budget-service/index.html), transactionUpdateEventBus: [TransactionUpdateEventBus](../../se.tink.android.repository.transaction/-transaction-update-event-bus/index.html), dispatcher: [DispatcherProvider](../../com.tink.service.util/-dispatcher-provider/index.html), dataRefreshHandler: [DataRefreshHandler](../../se.tink.android.repository.service/-data-refresh-handler/index.html)) : [Refreshable](../../se.tink.android.repository.service/-refreshable/index.html)



## Constructors


| | |
|---|---|
| [BudgetsRepository](-budgets-repository.html) | [androidJvm]<br>@Inject<br>fun [BudgetsRepository](-budgets-repository.html)(budgetService: [BudgetService](../../com.tink.service.budget/-budget-service/index.html), transactionUpdateEventBus: [TransactionUpdateEventBus](../../se.tink.android.repository.transaction/-transaction-update-event-bus/index.html), dispatcher: [DispatcherProvider](../../com.tink.service.util/-dispatcher-provider/index.html), dataRefreshHandler: [DataRefreshHandler](../../se.tink.android.repository.service/-data-refresh-handler/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [archiveBudget](archive-budget.html) | [androidJvm]<br>fun [archiveBudget](archive-budget.html)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), resultHandler: [ResultHandler](../../com.tink.service.handler/-result-handler/index.html)&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;) |
| [budgetPeriodDetails](budget-period-details.html) | [androidJvm]<br>fun [budgetPeriodDetails](budget-period-details.html)(budgetId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), start: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html), end: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html), resultHandler: [ResultHandler](../../com.tink.service.handler/-result-handler/index.html)&lt;[Pair](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)&lt;[BudgetSpecification](../../com.tink.model.budget/index.html#1357535401%2FClasslikes%2F1000845458), [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[BudgetPeriod](../../com.tink.model.budget/index.html#406477269%2FClasslikes%2F1000845458)&gt;&gt;&gt;) |
| [createOrUpdateBudget](create-or-update-budget.html) | [androidJvm]<br>fun [createOrUpdateBudget](create-or-update-budget.html)(descriptor: [BudgetCreateOrUpdateDescriptor](../../com.tink.model.budget/-budget-create-or-update-descriptor/index.html), resultHandler: [ResultHandler](../../com.tink.service.handler/-result-handler/index.html)&lt;[BudgetSpecification](../../com.tink.model.budget/index.html#1357535401%2FClasslikes%2F1000845458)&gt;) |
| [refresh](refresh.html) | [androidJvm]<br>open override fun [refresh](refresh.html)() |
| [requestBudgetPeriodDetailsState](request-budget-period-details-state.html) | [androidJvm]<br>fun [requestBudgetPeriodDetailsState](request-budget-period-details-state.html)(budgetId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), start: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html), end: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html)) |
| [transactions](transactions.html) | [androidJvm]<br>fun [transactions](transactions.html)(budgetId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), start: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html), end: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html)): [BudgetTransactionsLiveData](../-budget-transactions-live-data/index.html) |


## Properties


| Name | Summary |
|---|---|
| [budgetPeriodDetailsState](budget-period-details-state.html) | [androidJvm]<br>val [budgetPeriodDetailsState](budget-period-details-state.html): [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[ResponseState](../../com.tink.service.network/-response-state/index.html)&lt;[Pair](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)&lt;[BudgetSpecification](../../com.tink.model.budget/index.html#1357535401%2FClasslikes%2F1000845458), [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[BudgetPeriod](../../com.tink.model.budget/index.html#406477269%2FClasslikes%2F1000845458)&gt;&gt;&gt;&gt; |
| [budgets](budgets.html) | [androidJvm]<br>val [budgets](budgets.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[BudgetSummary](../../com.tink.model.budget/index.html#1968246694%2FClasslikes%2F1000845458)&gt;&gt; |
| [budgetsState](budgets-state.html) | [androidJvm]<br>val [budgetsState](budgets-state.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[ResponseState](../../com.tink.service.network/-response-state/index.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[BudgetSummary](../../com.tink.model.budget/index.html#1968246694%2FClasslikes%2F1000845458)&gt;&gt;&gt; |

