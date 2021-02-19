package com.tink.pfmui.insights.enrichment

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.tink.model.insights.Insight
import com.tink.model.insights.InsightData
import com.tink.model.misc.Amount
import com.tink.model.misc.ExactNumber
import se.tink.commons.extensions.divide
import se.tink.commons.extensions.minus
import javax.inject.Inject
import kotlin.math.roundToInt

internal class BudgetCreateSuggestionEnricher @Inject constructor() : InsightsEnricher {

    override fun enrich(input: LiveData<List<Insight>>): LiveData<List<Insight>> {
        return Transformations.map(input) { insightList ->
            insightList.onEach { insight ->
                insight.data.getViewDetails()?.let { insight.viewDetails = it }
            }
        }
    }

    private fun InsightData.getViewDetails(): Insight.ViewDetails? {
        val spentAmount: Amount? = when (this) {
            is InsightData.BudgetSuggestCreateTopCategoryData -> categorySpending.amount
            is InsightData.BudgetSuggestCreateTopPrimaryCategoryData -> categorySpending.amount
            else -> null
        }
        val suggestedAmount: Amount? = when (this) {
            is InsightData.BudgetSuggestCreateTopCategoryData -> suggestedBudgetAmount
            is InsightData.BudgetSuggestCreateTopPrimaryCategoryData -> suggestedBudgetAmount
            else -> null
        }
        if (spentAmount == null || suggestedAmount == null) return null
        val delta = (spentAmount - suggestedAmount).value
        val savePercentage = delta.divide(spentAmount.value).toBigDecimal().toDouble() * 100.0
        val savePerYear = delta.toBigDecimal().toDouble() * 12.0
        val savePerYearAmount = Amount(ExactNumber(savePerYear), spentAmount.currencyCode)
        return BudgetCreateSuggestionViewDetails(
            savePercentage = "-${savePercentage.roundToInt()}%",
            savePerYearAmount
        )
    }
}
