package com.tink.pfmsdk.insights.enrichment

import se.tink.core.models.insights.Insight

sealed class IconTypeViewDetails : Insight.ViewDetails {
    data class Category(val categoryCode: String) : IconTypeViewDetails()
    object Search : IconTypeViewDetails()
    object Tag : IconTypeViewDetails()
}

class BudgetSummaryViewDetails(
    val items: List<BudgetSummaryDetailItem>,
    val targetAmount: String,
    val spentAmount: String,
    val progress: Double
) : Insight.ViewDetails

class BudgetSummaryDetailItem(val budgetState: BudgetState, val iconTypeViewDetails: IconTypeViewDetails)

enum class BudgetState { SUCCESS, OVERSPENT }
