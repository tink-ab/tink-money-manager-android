package com.tink.pfmsdk

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * This class represents the features that are displayed as individual sections in the overview screen in the financial overview UI.
 * Each section acts as an entry point that end-users can interact with and navigate towards more detailed feature flows.
 */
sealed class OverviewFeature : Parcelable {
    /**
     * Represents the statistics charts section in the overview screen.
     *
     * @param statisticTypes A list of statistic types for which the charts are displayed
     */
    @Parcelize
    class Statistics(val statisticTypes: List<StatisticType>) : OverviewFeature()

    /**
     * Represents the latest transactions section in the overview screen.
     * This section includes a "See All" action text that the user can click to see a list of all transactions.
     */
    @Parcelize
    object LatestTransactions : OverviewFeature()

    /**
     * Represents the accounts list section in the overview screen.
     */
    @Parcelize
    object Accounts : OverviewFeature()

    @Parcelize
    object ActionableInsights : OverviewFeature()
}

/**
 * Represents the type of statistics data that can be displayed as a chart.
 */
enum class StatisticType {
    /**
     * Represents an end-user's expenses statistics data.
     */
    EXPENSES,
    /**
     * Represents an end-user's income statistics data.
     */
    INCOME
}

/**
 * A wrapper class containing a list of the [OverviewFeature] objects that users can pass as input
 * while creating an instance of the [FinanceOverviewFragment].
 *
 * Users can also add all the available features by simply calling [OverviewFeatures.ALL].
 */
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