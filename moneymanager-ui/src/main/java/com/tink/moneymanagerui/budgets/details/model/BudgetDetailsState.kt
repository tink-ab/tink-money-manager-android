package com.tink.moneymanagerui.budgets.details.model

import com.tink.model.budget.BudgetPeriod
import com.tink.model.budget.BudgetSpecification
import com.tink.service.network.ErrorState
import com.tink.service.network.LoadingState
import com.tink.service.network.ResponseState
import com.tink.service.network.SuccessState

data class BudgetDetailsState(
    val budget: ResponseState<BudgetSpecification> = LoadingState,
    val headerText: ResponseState<String> = LoadingState,
    val amountLeft: ResponseState<String> = LoadingState,
    val amountLeftColor: ResponseState<Int> = LoadingState,
    val totalAmount: ResponseState<String> = LoadingState,
    val progress: ResponseState<Double> = LoadingState,
    val budgetPeriodsList: ResponseState<List<BudgetPeriod>> = LoadingState
) {
    internal val overallState: ResponseState<BudgetDetailsData> =
        when {
            budget is SuccessState && headerText is SuccessState && amountLeft is SuccessState && amountLeftColor is SuccessState && totalAmount is SuccessState && progress is SuccessState && budgetPeriodsList is SuccessState
            -> {
                SuccessState(BudgetDetailsData(budget.data, headerText.data, amountLeft.data, amountLeftColor.data, totalAmount.data, progress.data, budgetPeriodsList.data))
            }
            budget is ErrorState || headerText is ErrorState || amountLeft is ErrorState || amountLeftColor is ErrorState || totalAmount is ErrorState || progress is ErrorState ||  budgetPeriodsList is ErrorState ->
                ErrorState("")
            else -> LoadingState
        }
}

data class BudgetDetailsData(
    val budget: BudgetSpecification,
    val headerText: String,
    val amountLeft: String,
    val amountLeftColor: Int,
    val totalAmount: String,
    val progress: Double,
    val budgetPeriodsList: List<BudgetPeriod>
)