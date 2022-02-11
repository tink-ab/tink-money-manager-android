package com.tink.moneymanagerui.budgets.details.model

import com.tink.model.budget.BudgetPeriod
import com.tink.model.budget.BudgetSpecification
import com.tink.service.network.ErrorState
import com.tink.service.network.LoadingState
import com.tink.service.network.ResponseState
import com.tink.service.network.SuccessState

data class BudgetSelectionState(
    val budget: ResponseState<BudgetSpecification> = LoadingState,
    val budgetPeriodsList: ResponseState<List<BudgetPeriod>> = LoadingState,
    val currentSelectedPeriod: ResponseState<BudgetPeriod> = LoadingState,
) {
    internal val overallState: ResponseState<BudgetSelectionData> =
        when {
            budget is SuccessState && budgetPeriodsList is SuccessState && currentSelectedPeriod is SuccessState -> {
                SuccessState(BudgetSelectionData(budget.data, budgetPeriodsList.data, currentSelectedPeriod.data))
            }
            budget is ErrorState ||  budgetPeriodsList is ErrorState || currentSelectedPeriod is ErrorState ->
                ErrorState("")
            else -> LoadingState

        }
}

data class BudgetSelectionData(
    val budget: BudgetSpecification,
    val budgetPeriodsList: List<BudgetPeriod>,
    val currentSelectedPeriod: BudgetPeriod,
)