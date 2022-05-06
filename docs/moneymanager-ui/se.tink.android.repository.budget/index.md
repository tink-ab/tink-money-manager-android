---
title: se.tink.android.repository.budget
---
//[moneymanager-ui](../../index.html)/[se.tink.android.repository.budget](index.html)



# Package se.tink.android.repository.budget



## Types


| Name | Summary |
|---|---|
| [BudgetsRepository](-budgets-repository/index.html) | [androidJvm]<br>@ExperimentalCoroutinesApi<br>class [BudgetsRepository](-budgets-repository/index.html)@Injectconstructor(budgetService: [BudgetService](../com.tink.service.budget/-budget-service/index.html), transactionUpdateEventBus: [TransactionUpdateEventBus](../se.tink.android.repository.transaction/-transaction-update-event-bus/index.html), dispatcher: [DispatcherProvider](../com.tink.service.util/-dispatcher-provider/index.html), dataRefreshHandler: [DataRefreshHandler](../se.tink.android.repository.service/-data-refresh-handler/index.html)) : [Refreshable](../se.tink.android.repository.service/-refreshable/index.html) |
| [BudgetTransactionsLiveData](-budget-transactions-live-data/index.html) | [androidJvm]<br>@ExperimentalCoroutinesApi<br>class [BudgetTransactionsLiveData](-budget-transactions-live-data/index.html)(budgetService: [BudgetService](../com.tink.service.budget/-budget-service/index.html), budgetId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), start: Instant, end: Instant, dispatcher: [DispatcherProvider](../com.tink.service.util/-dispatcher-provider/index.html), transactionUpdateEventBus: [TransactionUpdateEventBus](../se.tink.android.repository.transaction/-transaction-update-event-bus/index.html)) : [MediatorLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MediatorLiveData.html)&lt;[ErrorOrValue](../se.tink.android.livedata/-error-or-value/index.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[BudgetTransaction](../com.tink.model.budget/index.html#-1403204114%2FClasslikes%2F1000845458)&gt;&gt;&gt; |

