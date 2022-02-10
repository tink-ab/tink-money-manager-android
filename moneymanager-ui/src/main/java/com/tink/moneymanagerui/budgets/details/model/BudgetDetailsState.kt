package com.tink.moneymanagerui.budgets.details.model

import com.tink.model.budget.BudgetPeriod
import com.tink.model.misc.Amount
import com.tink.moneymanagerui.budgets.creation.specification.EXACT_NUMBER_ZERO
import com.tink.service.network.ErrorState
import com.tink.service.network.LoadingState
import com.tink.service.network.ResponseState
import com.tink.service.network.SuccessState
import org.threeten.bp.Instant

data class BudgetDetailsState(
    val headerText: ResponseState<String> = LoadingState,
    val amountLeft: ResponseState<String> = LoadingState,
    val amountLeftColor: ResponseState<Int> = LoadingState,
    val totalAmount: ResponseState<String> = LoadingState,
    val progress: ResponseState<Double> = LoadingState,
    val budgetPeriod: ResponseState<BudgetPeriod> = SuccessState(
        BudgetPeriod(Instant.now(), Instant.now(), Amount(EXACT_NUMBER_ZERO, "SEK"), Amount(EXACT_NUMBER_ZERO, "SEK"))),
    val budgetProgress: ResponseState<Double> = SuccessState(0.0)
    //val currentSelectedPeriod: ResponseState<BudgetPeriod> = LoadingState,
) {
    internal val overallState: ResponseState<BudgetDetailsData> =
        when {
            headerText is SuccessState && amountLeft is SuccessState && amountLeftColor is SuccessState && totalAmount is SuccessState && progress is SuccessState && budgetPeriod is SuccessState
                    && budgetProgress is SuccessState -> {
                SuccessState(BudgetDetailsData(headerText.data, amountLeft.data, amountLeftColor.data, totalAmount.data, progress.data, budgetPeriod.data, budgetProgress.data))
            }
            headerText is ErrorState || amountLeft is ErrorState || amountLeftColor is ErrorState || totalAmount is ErrorState || progress is ErrorState ||  budgetPeriod is ErrorState || budgetProgress is ErrorState ->
                ErrorState("")
            else -> LoadingState
        }
}

data class BudgetDetailsData(
    val headerText: String,
    val amountLeft: String,
    val amountLeftColor: Int,
    val totalAmount: String,
    val progress: Double,
    val budgetPeriod: BudgetPeriod,
    val budgetProgress: Double
)