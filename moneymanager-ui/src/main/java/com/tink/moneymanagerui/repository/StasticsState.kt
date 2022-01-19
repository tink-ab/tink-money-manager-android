package com.tink.moneymanagerui.repository

import com.tink.model.category.CategoryTree
import com.tink.model.statistics.Statistics
import com.tink.model.time.Period
import com.tink.model.user.UserProfile
import com.tink.moneymanagerui.overview.OverviewData
import com.tink.service.network.ResponseState
import com.tink.service.network.ErrorState
import com.tink.service.network.LoadingState
import com.tink.service.network.SuccessState

data class StatisticsState(
    val statistics: ResponseState<List<Statistics>> = LoadingState,
    val categories: ResponseState<CategoryTree> = LoadingState,
    val userProfile: ResponseState<UserProfile> = LoadingState,
    val period: ResponseState<Period?> = LoadingState,
) {

    internal val overallState: ResponseState<OverviewData> =
        when {
            statistics is SuccessState && categories is SuccessState && userProfile is SuccessState && period is SuccessState ->
                SuccessState(
                    OverviewData(
                        statistics.data,
                        period.data!!,
                        categories.data,
                        userProfile.data.currency
                    )
                )
            statistics is ErrorState || categories is ErrorState || userProfile is ErrorState || period is ErrorState ->
                ErrorState("")
            else -> LoadingState
        }
}
