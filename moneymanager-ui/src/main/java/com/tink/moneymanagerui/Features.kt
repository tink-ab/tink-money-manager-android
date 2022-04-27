package com.tink.moneymanagerui

import android.os.Parcelable
import com.tink.moneymanagerui.accounts.AccountEditConfiguration
import com.tink.moneymanagerui.accounts.AccountEditConfiguration.Companion.ALL
import com.tink.moneymanagerui.accounts.AccountGroupType
import com.tink.moneymanagerui.accounts.NoAccountGroup
import com.tink.moneymanagerui.accounts.OverviewAccountsMode
import com.tink.moneymanagerui.accounts.OverviewFavoriteAccounts
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
    class Accounts(
        val overviewAccountsMode: OverviewAccountsMode = OverviewFavoriteAccounts,
        val accountGroupType: AccountGroupType = NoAccountGroup,
        val accountEditConfiguration: AccountEditConfiguration = ALL
    ) : OverviewFeature()

    /**
     * Represents the actionable insights section in the overview screen.
     */
    @Parcelize
    object ActionableInsights : OverviewFeature()

    /**
     * Represents the budgets section in the overview screen.
     */
    @Parcelize
    object Budgets : OverviewFeature()

    /**
     * Represents a custom view container that can be added to the overview screen.
     *
     * This container view will be added as a FrameLayout in the overview screen which can be used
     * by users to add their own custom views.
     *
     * @param containerViewId The custom view container id
     * @param width The width of the container view, either MATCH_PARENT, WRAP_CONTENT or a fixed size in pixels
     * @param height The height of the container view, either MATCH_PARENT, WRAP_CONTENT or a fixed size in pixels
     */
    @Parcelize
    class CustomContainerView(
        val containerViewId: Int,
        val width: Int,
        val height: Int
    ) : OverviewFeature()
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
        @JvmField
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
                    OverviewFeature.Accounts(),
                    OverviewFeature.LatestTransactions,
                    OverviewFeature.Budgets
                )
            )
    }
}
