package com.tink.moneymanagerui.overview.charts.model

import com.tink.model.category.Category
import com.tink.model.time.Period
import com.tink.moneymanagerui.overview.charts.ChartSourceDataBase
import com.tink.service.network.ErrorState
import com.tink.service.network.LoadingState
import com.tink.service.network.ResponseState
import com.tink.service.network.SuccessState

data class ChartDetailsSourceState(
    val period: ResponseState<Period> = LoadingState,
    val category: ResponseState<Category> = LoadingState,
    val currency: ResponseState<String> = LoadingState,
    ) {

    internal val overallState: ResponseState<ChartSourceDataBase> =
        when {
            period is SuccessState && category is SuccessState && currency is SuccessState -> {
                SuccessState(ChartSourceDataBase(
                    period.data,
                    category.data,
                    currency.data,
                ))
            }
            period is ErrorState || category is ErrorState || currency is ErrorState ->
                ErrorState("")
            else -> LoadingState

        }
}