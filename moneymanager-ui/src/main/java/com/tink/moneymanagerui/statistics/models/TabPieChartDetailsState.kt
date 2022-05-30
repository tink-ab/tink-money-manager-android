package com.tink.moneymanagerui.statistics.models

import com.tink.model.category.Category
import com.tink.model.time.Period
import com.tink.model.user.UserProfile
import com.tink.moneymanagerui.overview.charts.TabPieChartData
import com.tink.service.network.ErrorState
import com.tink.service.network.LoadingState
import com.tink.service.network.ResponseState
import com.tink.service.network.SuccessState

data class TabPieChartDetailsState(
    val selectedPeriod: ResponseState<Period> = LoadingState,
    val periods: ResponseState<List<Period>> = LoadingState,
    val category: ResponseState<Category> = LoadingState,
    val userProfile: ResponseState<UserProfile> = LoadingState
) {

    internal val overallState: ResponseState<TabPieChartData> =
        when {
            selectedPeriod is SuccessState && periods is SuccessState && category is SuccessState && userProfile is SuccessState ->
                SuccessState(
                    TabPieChartData(
                        selectedPeriod.data,
                        periods.data,
                        category.data,
                        userProfile.data
                    )
                )
            selectedPeriod is ErrorState || periods is ErrorState || category is ErrorState || userProfile is ErrorState ->
                ErrorState("")
            else -> LoadingState
        }
}
