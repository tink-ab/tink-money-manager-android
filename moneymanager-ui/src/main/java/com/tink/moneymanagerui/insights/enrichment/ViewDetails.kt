package com.tink.moneymanagerui.insights.enrichment

import android.os.Parcelable
import com.tink.model.insights.Insight
import com.tink.model.misc.Amount
import kotlinx.android.parcel.Parcelize

sealed class IconTypeViewDetails : Insight.ViewDetails {

    @Parcelize
    data class Category(val categoryCode: String) : IconTypeViewDetails()

    @Parcelize
    object Search : IconTypeViewDetails()

    @Parcelize
    object Tag : IconTypeViewDetails()

    @Parcelize
    object Expenses : IconTypeViewDetails()
}

@Parcelize
class BudgetSummaryViewDetails(
    val items: List<BudgetSummaryDetailItem>,
    val targetAmount: String,
    val spentAmount: String,
    val progress: Double
) : Insight.ViewDetails

@Parcelize
class BudgetSummaryDetailItem(
    val budgetState: BudgetState,
    val iconTypeViewDetails: IconTypeViewDetails
) : Parcelable

@Parcelize
enum class BudgetState : Parcelable { SUCCESS, OVERSPENT }

@Parcelize
class BudgetCreateSuggestionViewDetails(
    val savePercentage: String,
    val savePerYearAmount: Amount
) : Insight.ViewDetails
