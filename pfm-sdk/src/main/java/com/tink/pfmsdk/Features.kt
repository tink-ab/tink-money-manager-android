package com.tink.pfmsdk

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class OverviewFeature : Parcelable {
    @Parcelize
    class Statistics(val statisticTypes: List<StatisticType>) : OverviewFeature()

    @Parcelize
    object LatestTransactions : OverviewFeature()

    @Parcelize
    object Accounts : OverviewFeature()

    @Parcelize
    object ActionableInsights : OverviewFeature()
}

enum class StatisticType {
    EXPENSES,
    INCOME
}

@Parcelize
data class OverviewFeatures(val features: List<OverviewFeature>) : Parcelable {
    companion object {
        val ALL =
            OverviewFeatures(
                listOf(
                    OverviewFeature.ActionableInsights,
                    OverviewFeature.Statistics(
                        listOf(
                            StatisticType.EXPENSES,
                            StatisticType.INCOME
                        )
                    ),
                    OverviewFeature.Accounts,
                    OverviewFeature.LatestTransactions
                )
            )
    }
}