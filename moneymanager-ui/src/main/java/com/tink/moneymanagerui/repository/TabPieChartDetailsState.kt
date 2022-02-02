package com.tink.moneymanagerui.repository

import com.tink.model.category.Category
import com.tink.model.time.Period
import com.tink.model.user.UserProfile
import com.tink.moneymanagerui.overview.charts.TabPieChartData
import com.tink.service.network.ErrorState
import com.tink.service.network.LoadingState
import com.tink.service.network.ResponseState
import com.tink.service.network.SuccessState

data class StatisticsDetailsState(
    val periods: ResponseState<List<Period>> = LoadingState,
    val selectedPeriod: ResponseState<Period> = LoadingState,
    val category: ResponseState<Category> = LoadingState,
    val userProfile: ResponseState<UserProfile> = LoadingState,
) {

    internal val overallState: ResponseState<TabPieChartData> =
        when {
            periods is SuccessState && selectedPeriod is SuccessState && category is SuccessState && userProfile is SuccessState ->
                SuccessState(
                    TabPieChartData(
                        periods.data,
                        selectedPeriod.data,
                        category.data,
                        userProfile.data,

                    )
                )
            category is ErrorState || userProfile is ErrorState || periods is ErrorState ->
                ErrorState("")
            else -> LoadingState
        }
}
